package elixir.of.things.generator

import org.eclipse.emf.ecore.resource.Resource
import org.eclipse.xtext.generator.AbstractGenerator
import org.eclipse.xtext.generator.IFileSystemAccess2
import org.eclipse.xtext.generator.IGeneratorContext
import elixir.of.things.elixirOfThings.System
import elixir.of.things.elixirOfThings.Node
import elixir.of.things.elixirOfThings.Sensor
import elixir.of.things.elixirOfThings.Actuator
import elixir.of.things.elixirOfThings.Coordinator
import elixir.of.things.elixirOfThings.Topic
import elixir.of.things.elixirOfThings.Operator
import elixir.of.things.elixirOfThings.BoolExpr
import elixir.of.things.elixirOfThings.BoolOrExpr
import elixir.of.things.elixirOfThings.BoolAndExpr
import elixir.of.things.elixirOfThings.BoolNotExpr
import elixir.of.things.elixirOfThings.TopicRef
import elixir.of.things.elixirOfThings.NumExpr
import elixir.of.things.elixirOfThings.NumLiteral
import elixir.of.things.elixirOfThings.NumAddExpr
import elixir.of.things.elixirOfThings.NumSubExpr
import elixir.of.things.elixirOfThings.NumMulExpr
import elixir.of.things.elixirOfThings.NumDivExpr
import elixir.of.things.elixirOfThings.SampleRate

class ElixirOfThingsGenerator extends AbstractGenerator {

    // Helper: snake_case -> CamelCase
    def toCamelCase(String name) {
        name.split("_").map[toFirstUpper].join("")
    }

    // Helper: Operator enum -> Elixir operator string
    def toElixirOp(Operator op) {
        switch (op) {
            case Operator.GREATER_THAN: ">"
            case Operator.LESS_THAN:    "<"
            case Operator.GREATER_EQ:   ">="
            case Operator.LESS_EQ:      "<="
            case Operator.EQUALS:       "=="
            default:                    "=="
        }
    }

    // Helper: get topicString from Topic object
    def topicStr(Topic t) {
        t.topicString
    }

    // Helper: evaluates arithmetic expression at code generation time
    def int evalNumExpr(NumExpr expr) {
        if (expr instanceof NumLiteral)
            expr.value
        else if (expr instanceof NumAddExpr)
            evalNumExpr(expr.left) + evalNumExpr(expr.right)
        else if (expr instanceof NumSubExpr)
            evalNumExpr(expr.left) - evalNumExpr(expr.right)
        else if (expr instanceof NumMulExpr)
            evalNumExpr(expr.left) * evalNumExpr(expr.right)
        else if (expr instanceof NumDivExpr)
            evalNumExpr(expr.left) / evalNumExpr(expr.right)
        else
            0
    }

    // Helper: SampleRate -> milliseconds
    def int sampleRateToMillis(SampleRate sr) {
        val v = evalNumExpr(sr.value)
        switch (sr.unit.toString) {
            case "SEC": v * 1000
            case "MIN": v * 60000
            case "MS":  v
            default:    v * 1000
        }
    }

    // Helper: duration -> milliseconds (same as sampleRate)
    def int durationToMillis(SampleRate sr) {
        sampleRateToMillis(sr)
    }

    // Helper: recursively traverse BoolExpr tree -> Elixir condition
    def String generateBoolExpr(BoolExpr expr) {
        if (expr instanceof BoolOrExpr)
            '''(«generateBoolExpr(expr.left)» or «generateBoolExpr(expr.right)»)'''
        else if (expr instanceof BoolAndExpr)
            '''(«generateBoolExpr(expr.left)» and «generateBoolExpr(expr.right)»)'''
        else if (expr instanceof BoolNotExpr)
            // FIX 2: not (expr) instead of (not expr)
            '''not («generateBoolExpr(expr.operand)»)'''
        else if (expr instanceof TopicRef) {
            val parts = topicStr(expr.topic).split("/")
            '''state.«parts.get(0)» == :«parts.get(1)»'''
        } else
            '''true'''
    }

    // Helper: extract unique sensor key names from subscribed topics

    def uniqueStateKeys(Coordinator coord) {
        coord.subscribeTo
            .map[topicStr(it).split("/").get(0)]
            .toSet
    }

    // Entry point
    override void doGenerate(Resource resource,
                             IFileSystemAccess2 fsa,
                             IGeneratorContext context) {

        val system = resource.contents.head as System

        for (node : system.nodes) {
            val nodeName = node.name.toFirstLower
            val prefix   = node.name.toCamelCase + "Node"

            for (sensor : system.sensors.filter[deployedOn == node]) {
                fsa.generateFile(
                    '''lib/«nodeName»/«sensor.name.toFirstLower».ex''',
                    generateSensor(sensor, prefix, node)
                )
            }

            for (actuator : system.actuators.filter[deployedOn == node]) {
                fsa.generateFile(
                    '''lib/«nodeName»/«actuator.name.toFirstLower».ex''',
                    generateActuator(actuator, prefix)
                )
            }

            val coord = system.coordinators.findFirst[deployedOn == node]
            if (coord !== null) {
                fsa.generateFile(
                    '''lib/«nodeName»/coordinator.ex''',
                    generateCoordinator(coord, prefix, system)
                )
                fsa.generateFile(
                    '''lib/«nodeName»/mqtt_handler.ex''',
                    generateMqttHandler(coord, prefix)
                )
            }

            fsa.generateFile(
                '''lib/«nodeName»/application.ex''',
                generateApplication(system, node, prefix)
            )            
        }
        
        fsa.generateFile(
          '''dashboard/topology.json''',
          generateTopologyJson(system)
        )
        
        fsa.generateFile(
          '''dashboard/topics.json''',
          generateTopicsJson(system)
        )
	}

    // application.ex
    def generateApplication(System system, Node node, String prefix) '''
        defmodule «prefix».Application do
          use Application

          def start(_type, _args) do
            «val coord = system.coordinators.findFirst[deployedOn == node]»
            «val hasCoord = coord !== null»
            tortoise_opts = [
              client_id: "«node.name»_«IF hasCoord»coordinator«ELSE»sensor«ENDIF»_node",
              server: {Tortoise311.Transport.Tcp,
                       host: "«system.broker.host»",
                       port: «system.broker.port»},
              handler: {«IF hasCoord»«prefix».MqttHandler«ELSE»Tortoise311.Handler.Logger«ENDIF», []}«IF hasCoord»,
              subscriptions: [
                «FOR sub : coord.subscribeTo SEPARATOR ","»
                {"«topicStr(sub)»", 1}
                «ENDFOR»
              ]
              «ENDIF»
            ]

            children =
              [{Tortoise311.Connection, tortoise_opts}]
              «FOR sensor : system.sensors.filter[deployedOn == node]»
              ++ [«prefix».«sensor.name.toCamelCase»]
              «ENDFOR»
              «FOR actuator : system.actuators.filter[deployedOn == node]»
              ++ [«prefix».«actuator.name.toCamelCase»]
              «ENDFOR»
              «IF hasCoord»
              ++ [«prefix».Coordinator]
              «ENDIF»

            Supervisor.start_link(children,
              strategy: :one_for_one,
              name: «prefix».Supervisor)
          end
        end
    '''

    // <sensor_name>.ex
    def generateSensor(Sensor sensor, String prefix, Node node) '''
        defmodule «prefix».«sensor.name.toCamelCase» do
          use GenServer
          require Logger

          def start_link(_),
            do: GenServer.start_link(__MODULE__, nil, name: __MODULE__)

          «IF sensor.type.toString == "TEMP_DS18B20"»
          # DS18B20 Temperature Sensor (1-Wire)
          # sampleRate: «evalNumExpr(sensor.sampleRate.value)» «sensor.sampleRate.unit» = «sampleRateToMillis(sensor.sampleRate)» ms
          # GPIO «sensor.gpioPin» 

          def init(_) do
            :timer.send_interval(«sampleRateToMillis(sensor.sampleRate)», :read_sensor)
            {:ok, %{last_value: nil}}
          end

          def handle_info(:read_sensor, state) do
            case Path.wildcard("/sys/bus/w1/devices/28-*") do
              [sensor_path | _] ->
                case File.read(Path.join(sensor_path, "w1_slave")) do
                  {:ok, data} ->
                    case Regex.run(~r/t=(-?\d+)/, data) do
                      [_, raw] ->
                        value = String.to_integer(raw) / 1000.0
                        Logger.debug("«sensor.name»: #{value}°C")
                        «FOR trigger : sensor.triggers»
                        # Trigger «trigger.name»: when value «toElixirOp(trigger.condition.operator)» «evalNumExpr(trigger.condition.right)»
                        if value «toElixirOp(trigger.condition.operator)» «evalNumExpr(trigger.condition.right)» do
                          «FOR action : trigger.actions»
                          Tortoise311.publish(
                            "«node.name»_sensor_node",
                            "«topicStr(action.topic)»",
                            "#{value}",
                            qos: 1
                          )
                          «ENDFOR»
                        end
                        «ENDFOR»
                        {:noreply, %{last_value: value}}
                      _ ->
                        Logger.warning("«sensor.name»: failed to parse data")
                        {:noreply, state}
                    end
                  {:error, reason} ->
                    Logger.warning("«sensor.name»: read error #{inspect(reason)}")
                    {:noreply, state}
                end
              [] ->
                Logger.warning("«sensor.name»: no 1-Wire device found")
                {:noreply, state}
            end
          end

          «ELSEIF sensor.type.toString == "TEMP_DHT22"»
          # DHT22 Temperature Sensor
          # sampleRate: «evalNumExpr(sensor.sampleRate.value)» «sensor.sampleRate.unit» = «sampleRateToMillis(sensor.sampleRate)» ms
          # GPIO «sensor.gpioPin»

          def init(_) do
            :timer.send_interval(«sampleRateToMillis(sensor.sampleRate)», :read_sensor)
            {:ok, %{last_value: nil}}
          end

          def handle_info(:read_sensor, state) do
            {:ok, gpio} = Circuits.GPIO.open(«sensor.gpioPin», :input)
            value = Circuits.GPIO.read(gpio)
            Circuits.GPIO.close(gpio)
            Logger.debug("«sensor.name»: #{value}")
            «FOR trigger : sensor.triggers»
            # Trigger «trigger.name»: when value «toElixirOp(trigger.condition.operator)» «evalNumExpr(trigger.condition.right)»
            if value «toElixirOp(trigger.condition.operator)» «evalNumExpr(trigger.condition.right)» do
              «FOR action : trigger.actions»
              Tortoise311.publish(
                "«node.name»_sensor_node",
                "«topicStr(action.topic)»",
                "#{value}",
                qos: 1
              )
              «ENDFOR»
            end
            «ENDFOR»
            {:noreply, %{last_value: value}}
          end

          «ELSEIF sensor.type.toString == "MOTION_PIR"»
          # HC-SR501 PIR Motion Sensor (interrupt-driven)
          # No polling — GPIO «sensor.gpioPin» interrupt fires instantly on change

          def init(_) do
            {:ok, gpio} = Circuits.GPIO.open(«sensor.gpioPin», :input)
            Circuits.GPIO.set_interrupts(gpio, :both)
            Logger.info("«sensor.name»: listening on GPIO «sensor.gpioPin»")
            {:ok, gpio}
          end

          «FOR trigger : sensor.triggers»
          # Trigger «trigger.name»: when pin_value «toElixirOp(trigger.condition.operator)» «evalNumExpr(trigger.condition.right)»
          def handle_info({:circuits_gpio, «sensor.gpioPin», _timestamp, pin_value}, state)
              when pin_value «toElixirOp(trigger.condition.operator)» «evalNumExpr(trigger.condition.right)» do
            Logger.info("«sensor.name»: «trigger.name» fired (value=#{pin_value})")
            «FOR action : trigger.actions»
            Tortoise311.publish(
              "«node.name»_sensor_node",
              "«topicStr(action.topic)»",
              "#{pin_value}",
              qos: 1
            )
            «ENDFOR»
            {:noreply, state}
          end

          «ENDFOR»
          «ENDIF»

          def handle_info(_message, state), do: {:noreply, state}
        end
    '''

    // <actuator_name>.ex
    def generateActuator(Actuator actuator, String prefix) '''
        defmodule «prefix».«actuator.name.toCamelCase» do
          use GenServer
          require Logger

          def turn_on(),  do: GenServer.cast(__MODULE__, :turn_on)
          def turn_off(), do: GenServer.cast(__MODULE__, :turn_off)

          def start_link(_),
            do: GenServer.start_link(__MODULE__, nil, name: __MODULE__)

          def init(_) do
            {:ok, gpio} = Circuits.GPIO.open(«actuator.gpioPin», :output)
            Circuits.GPIO.write(gpio, 0)
            Logger.info("«actuator.name»: ready on GPIO «actuator.gpioPin»")
            {:ok, gpio}
          end

          def handle_cast(:turn_on, gpio) do
            Circuits.GPIO.write(gpio, 1)
            Logger.info("«actuator.name»: ON")
            {:noreply, gpio}
          end

          def handle_cast(:turn_off, gpio) do
            Circuits.GPIO.write(gpio, 0)
            Logger.info("«actuator.name»: OFF")
            {:noreply, gpio}
          end

          «FOR msg : actuator.messages»
          «IF msg.duration !== null»
          # on message from «msg.topic.name»: turn «msg.state» for «evalNumExpr(msg.duration.value)» «msg.duration.unit»
          def handle_cast({:timed_on, "«topicStr(msg.topic)»"}, gpio) do
            Circuits.GPIO.write(gpio, 1)
            Logger.info("«actuator.name»: ON for «durationToMillis(msg.duration)»ms")
            Process.sleep(«durationToMillis(msg.duration)»)
            Circuits.GPIO.write(gpio, 0)
            Logger.info("«actuator.name»: OFF (timer expired)")
            {:noreply, gpio}
          end

          «ENDIF»
          «ENDFOR»
        end
    '''

    // mqtt_handler.ex
    def generateMqttHandler(Coordinator coord, String prefix) '''
        defmodule «prefix».MqttHandler do
          use Tortoise311.Handler
          require Logger

          def init(args), do: {:ok, args}
          def connection(_status, state), do: {:ok, state}

          «FOR topic : coord.subscribeTo»
          «val parts = topicStr(topic).split("/")»
          # «topic.name» = "«topicStr(topic)»"
          def handle_message(["«parts.get(0)»", "«parts.get(1)»"], _payload, state) do
            Logger.debug("MqttHandler: received «topicStr(topic)»")
            «prefix».Coordinator.update_state(
              :«parts.get(0)»,
              :«parts.get(1)»
            )
            {:ok, state}
          end

          «ENDFOR»
          def subscription(_status, _topic_filter, state), do: {:ok, state}
          def terminate(_reason, _state), do: :ok
        end
    '''

    // coordinator.ex
    def generateCoordinator(Coordinator coord, String prefix, System system) '''
        defmodule «prefix».Coordinator do
          use GenServer
          require Logger

          def update_state(sensor, value) do
            GenServer.cast(__MODULE__, {:update, sensor, value})
          end

          def start_link(_),
            do: GenServer.start_link(__MODULE__, nil, name: __MODULE__)

          def init(_) do
            initial = %{
              «FOR key : uniqueStateKeys(coord) SEPARATOR ","»
              «key»: :unknown
              «ENDFOR»
            }
            Logger.info("Coordinator started: #{inspect(initial)}")
            {:ok, initial}
          end

          def handle_cast({:update, sensor, value}, state) do
            new_state = Map.put(state, sensor, value)
            Logger.debug("Coordinator state: #{inspect(new_state)}")
            evaluate_rules(new_state)
            {:noreply, new_state}
          end

          defp evaluate_rules(state) do
            «FOR rule : coord.rules»
            # Rule: «rule.name»
            if «generateBoolExpr(rule.condition.expression)» do
              Logger.error("ALERT [«rule.name»]: «rule.actions.get(0).message»")
              Tortoise311.publish(
                "«coord.deployedOn.name»_coordinator_node",
                "«topicStr(rule.actions.get(0).topic)»",
                ~s({"message": "«rule.actions.get(0).message»"}),
                qos: 1
              )
            end
            «ENDFOR»

            «FOR actuator : system.actuators.filter[deployedOn == coord.deployedOn]»
            «val onParts = topicStr(actuator.subscribeTo.get(0)).split("/")»
            # «actuator.name» («actuator.type» on GPIO «actuator.gpioPin»)
            if state.«onParts.get(0)» == :«onParts.get(1)»,
              do: «prefix».«actuator.name.toCamelCase».turn_on(),
              else: «prefix».«actuator.name.toCamelCase».turn_off()
            «ENDFOR»
          end
        end
    '''
    
    def generateTopologyJson(System system) '''
	{
	  "systemName": "«system.name»",
	  "broker": {
	    "host": «jsonString(system.broker.host)»,
	    "port": «system.broker.port»
	  },
	  "nodes": [
	    «FOR node : system.nodes SEPARATOR ","»
	    {
	      "id": «jsonString(node.name)»,
	      "name": «jsonString(node.name)»
	    }
	    «ENDFOR»
	  ],
	  "sensors": [
	    «FOR sensor : system.sensors SEPARATOR ","»
	    {
	      "id": «jsonString(sensor.name)»,
	      "name": «jsonString(sensor.name)»,
	      "type": «jsonString(sensor.type.toString)»,
	      "gpioPin": «sensor.gpioPin»,
	      "sampleRateMs": «sampleRateToMillis(sensor.sampleRate)»,
	      "deployedOnNodeId": «jsonString(sensor.deployedOn.name)»
	    }
	    «ENDFOR»
	  ],
	  "actuators": [
	    «FOR actuator : system.actuators SEPARATOR ","»
	    {
	      "id": «jsonString(actuator.name)»,
	      "name": «jsonString(actuator.name)»,
	      "type": «jsonString(actuator.type.toString)»,
	      "gpioPin": «actuator.gpioPin»,
	      "deployedOnNodeId": «jsonString(actuator.deployedOn.name)»
	    }
	    «ENDFOR»
	  ],
	  "coordinators": [
	    «FOR coord : system.coordinators SEPARATOR ","»
	    {
	      "id": «jsonString(coord.name)»,
	      "name": «jsonString(coord.name)»,
	      "deployedOnNodeId": «jsonString(coord.deployedOn.name)»
	    }
	    «ENDFOR»
	  ]
	}
	'''
	
	def generateTopicsJson(System system) '''
	{
	  "systemName": "«system.name»",
	  "broker": {
	    "host": «jsonString(system.broker.host)»,
	    "port": «system.broker.port»
	  },
	  "topics": [
	    «FOR topic : system.topics SEPARATOR ","»
	    {
	      "id": «jsonString(topic.name)»,
	      "name": «jsonString(topic.name)»,
	      "path": «jsonString(topic.topicString)»,
	      "qos": «topic.qos.ordinal»,
	      "producers": [
	        «val producerIds = findProducers(system, topic)»
	        «FOR p : producerIds SEPARATOR ","»
	        «jsonString(p)»
	        «ENDFOR»
	      ],
	      "consumers": [
	        «val consumerIds = findConsumers(system, topic)»
	        «FOR c : consumerIds SEPARATOR ","»
	        «jsonString(c)»
	        «ENDFOR»
	      ]
	    }
	    «ENDFOR»
	  ]
	}
	'''
	
	def jsonString(String value) {
	  '"' + value.replace("\\", "\\\\").replace("\"", "\\\"") + '"'
	}
	
	def findProducers(System system, Topic topic) {
	  val ids = newArrayList
	
	  for (sensor : system.sensors) {
	    for (trigger : sensor.triggers) {
	      if (trigger.actions.exists[action | action.topic == topic]) {
	        ids.add(sensor.name)
	      }
	    }
	  }
	
	  for (coord : system.coordinators) {
	    if (coord.rules.exists[rule |
	      rule.actions.exists[action | action.topic == topic]
	    ]) {
	      ids.add(coord.name)
	    }
	  }
	
	  ids.toSet.toList
	}
	
	def findConsumers(System system, Topic topic) {
	  val ids = newArrayList
	
	  for (coord : system.coordinators) {
	    if (coord.subscribeTo.contains(topic)) {
	      ids.add(coord.name)
	    }
	  }
	
	  for (actuator : system.actuators) {
	    if (actuator.subscribeTo.contains(topic)) {
	      ids.add(actuator.name)
	    }
	  }
	
	  ids.toSet.toList
	}
}

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

class ElixirOfThingsGenerator extends AbstractGenerator {

    // Helper: converts snake_case to CamelCase
    // e.g. "motion_sensor" → "MotionSensor"
    def toCamelCase(String name) {
        name.split("_").map[toFirstUpper].join("")
    }

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
    }

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
	            {"«sub»", 1}
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

    def generateSensor(Sensor sensor, String prefix, Node node) '''
        defmodule «prefix».«sensor.name.toCamelCase» do
          use GenServer
          require Logger

          def start_link(_),
            do: GenServer.start_link(__MODULE__, nil, name: __MODULE__)

          «IF sensor.type.toString == "TEMPERATURE"»
          def init(_) do
            :timer.send_interval(«sensor.sampleRate.value * 1000», :read_temp)
            {:ok, %{last_state: :low}}
          end

          def handle_info(:read_temp, state) do
            case Path.wildcard("/sys/bus/w1/devices/28-*") do
              [sensor_path | _] ->
                case File.read(Path.join(sensor_path, "w1_slave")) do
                  {:ok, data} ->
                    case Regex.run(~r/t=(-?\d+)/, data) do
                      [_, temp_string] ->
                        temp_c = String.to_integer(temp_string) / 1000.0
                        current = if temp_c > 25.0, do: :high, else: :low

                        if current != state.last_state do
                          topic = if current == :high,
                            do: "temperature/high",
                            else: "temperature/low"
                          Tortoise311.publish(
                            "«node.name»_sensor_node",
                            topic,
                            "#{temp_c}",
                            qos: 1
                          )
                        end
                        {:noreply, %{last_state: current}}

                      _ -> {:noreply, state}
                    end
                  _ -> {:noreply, state}
                end
              [] -> {:noreply, state}
            end
          end

          «ELSEIF sensor.type.toString == "MOTION"»
          def init(_) do
            {:ok, gpio} = Circuits.GPIO.open(«sensor.gpioPin», :input)
            Circuits.GPIO.set_interrupts(gpio, :both)
            {:ok, gpio}
          end

          def handle_info({:circuits_gpio, «sensor.gpioPin», _timestamp, 1}, state) do
            Logger.warning("Motion detected! Publishing.")
            Tortoise311.publish(
              "«node.name»_sensor_node",
              "motion/detected",
              "true",
              qos: 1
            )
            {:noreply, state}
          end

          def handle_info({:circuits_gpio, «sensor.gpioPin», _timestamp, 0}, state) do
            Logger.info("Motion cleared. Publishing still.")
            Tortoise311.publish(
              "«node.name»_sensor_node",
              "motion/still",
              "false",
              qos: 1
            )
            {:noreply, state}
          end
          «ENDIF»

          def handle_info(_message, state), do: {:noreply, state}
        end
    '''

    def generateActuator(Actuator actuator, String prefix) '''
        defmodule «prefix».«actuator.name.toCamelCase» do
          use GenServer

          def turn_on(),  do: GenServer.cast(__MODULE__, :turn_on)
          def turn_off(), do: GenServer.cast(__MODULE__, :turn_off)

          def start_link(_),
            do: GenServer.start_link(__MODULE__, nil, name: __MODULE__)

          def init(_) do
            {:ok, gpio} = Circuits.GPIO.open(«actuator.gpioPin», :output)
            Circuits.GPIO.write(gpio, 0)
            {:ok, gpio}
          end

          def handle_cast(:turn_on, gpio) do
            Circuits.GPIO.write(gpio, 1)
            {:noreply, gpio}
          end

          def handle_cast(:turn_off, gpio) do
            Circuits.GPIO.write(gpio, 0)
            {:noreply, gpio}
          end
        end
    '''

    def generateMqttHandler(Coordinator coord, String prefix) '''
        defmodule «prefix».MqttHandler do
          use Tortoise311.Handler
          require Logger

          def init(args), do: {:ok, args}
          def connection(_status, state), do: {:ok, state}

          «FOR topic : coord.subscribeTo»
          «val parts = topic.split("/")»
          def handle_message(["«parts.get(0)»", "«parts.get(1)»"], _payload, state) do
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
            {:ok, %{temperature: :low, motion: :still}}
          end

          def handle_cast({:update, sensor, value}, state) do
            new_state = Map.put(state, sensor, value)
            evaluate_rules(new_state)
            {:noreply, new_state}
          end

          defp evaluate_rules(state) do
            «FOR rule : coord.rules»
            # DSL Rule: «rule.name»
            if «FOR topic : rule.condition.topics SEPARATOR " and "»state.«topic.segments.get(0)» == :«topic.segments.get(1)»«ENDFOR» do
              Logger.error("ALERT: «rule.actions.get(0).message»")
              Tortoise311.publish(
                "«coord.deployedOn.name»_coordinator_node",
                "alerts/warning",
                ~s({"message": "«rule.actions.get(0).message»"}),
                qos: 1
              )
            end
            «ENDFOR»

            «FOR actuator : system.actuators.filter[deployedOn == coord.deployedOn]»
            «IF actuator.type.toString == "LED"»
            if state.temperature == :high,
              do: «prefix».«actuator.name.toCamelCase».turn_on(),
              else: «prefix».«actuator.name.toCamelCase».turn_off()
            «ELSEIF actuator.type.toString == "BUZZER"»
            if state.motion == :detected,
              do: «prefix».«actuator.name.toCamelCase».turn_on(),
              else: «prefix».«actuator.name.toCamelCase».turn_off()
            «ENDIF»
            «ENDFOR»
          end
        end
    '''
}
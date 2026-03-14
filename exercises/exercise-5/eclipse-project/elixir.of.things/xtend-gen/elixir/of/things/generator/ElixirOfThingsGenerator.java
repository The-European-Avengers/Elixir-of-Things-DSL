package elixir.of.things.generator;

import elixir.of.things.elixirOfThings.Actuator;
import elixir.of.things.elixirOfThings.Coordinator;
import elixir.of.things.elixirOfThings.Node;
import elixir.of.things.elixirOfThings.Rule;
import elixir.of.things.elixirOfThings.Sensor;
import elixir.of.things.elixirOfThings.TopicPath;
import java.util.List;
import java.util.Objects;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.xtend2.lib.StringConcatenation;
import org.eclipse.xtext.generator.AbstractGenerator;
import org.eclipse.xtext.generator.IFileSystemAccess2;
import org.eclipse.xtext.generator.IGeneratorContext;
import org.eclipse.xtext.xbase.lib.Conversions;
import org.eclipse.xtext.xbase.lib.Functions.Function1;
import org.eclipse.xtext.xbase.lib.IterableExtensions;
import org.eclipse.xtext.xbase.lib.ListExtensions;
import org.eclipse.xtext.xbase.lib.StringExtensions;

@SuppressWarnings("all")
public class ElixirOfThingsGenerator extends AbstractGenerator {
  public String toCamelCase(final String name) {
    final Function1<String, String> _function = (String it) -> {
      return StringExtensions.toFirstUpper(it);
    };
    return IterableExtensions.join(ListExtensions.<String, String>map(((List<String>)Conversions.doWrapArray(name.split("_"))), _function), "");
  }

  @Override
  public void doGenerate(final Resource resource, final IFileSystemAccess2 fsa, final IGeneratorContext context) {
    EObject _head = IterableExtensions.<EObject>head(resource.getContents());
    final elixir.of.things.elixirOfThings.System system = ((elixir.of.things.elixirOfThings.System) _head);
    EList<Node> _nodes = system.getNodes();
    for (final Node node : _nodes) {
      {
        final String nodeName = StringExtensions.toFirstLower(node.getName());
        String _camelCase = this.toCamelCase(node.getName());
        final String prefix = (_camelCase + "Node");
        final Function1<Sensor, Boolean> _function = (Sensor it) -> {
          Node _deployedOn = it.getDeployedOn();
          return Boolean.valueOf(Objects.equals(_deployedOn, node));
        };
        Iterable<Sensor> _filter = IterableExtensions.<Sensor>filter(system.getSensors(), _function);
        for (final Sensor sensor : _filter) {
          StringConcatenation _builder = new StringConcatenation();
          _builder.append("lib/");
          _builder.append(nodeName);
          _builder.append("/");
          String _firstLower = StringExtensions.toFirstLower(sensor.getName());
          _builder.append(_firstLower);
          _builder.append(".ex");
          fsa.generateFile(_builder.toString(), 
            this.generateSensor(sensor, prefix, node));
        }
        final Function1<Actuator, Boolean> _function_1 = (Actuator it) -> {
          Node _deployedOn = it.getDeployedOn();
          return Boolean.valueOf(Objects.equals(_deployedOn, node));
        };
        Iterable<Actuator> _filter_1 = IterableExtensions.<Actuator>filter(system.getActuators(), _function_1);
        for (final Actuator actuator : _filter_1) {
          StringConcatenation _builder_1 = new StringConcatenation();
          _builder_1.append("lib/");
          _builder_1.append(nodeName);
          _builder_1.append("/");
          String _firstLower_1 = StringExtensions.toFirstLower(actuator.getName());
          _builder_1.append(_firstLower_1);
          _builder_1.append(".ex");
          fsa.generateFile(_builder_1.toString(), 
            this.generateActuator(actuator, prefix));
        }
        final Function1<Coordinator, Boolean> _function_2 = (Coordinator it) -> {
          Node _deployedOn = it.getDeployedOn();
          return Boolean.valueOf(Objects.equals(_deployedOn, node));
        };
        final Coordinator coord = IterableExtensions.<Coordinator>findFirst(system.getCoordinators(), _function_2);
        if ((coord != null)) {
          StringConcatenation _builder_2 = new StringConcatenation();
          _builder_2.append("lib/");
          _builder_2.append(nodeName);
          _builder_2.append("/coordinator.ex");
          fsa.generateFile(_builder_2.toString(), 
            this.generateCoordinator(coord, prefix, system));
          StringConcatenation _builder_3 = new StringConcatenation();
          _builder_3.append("lib/");
          _builder_3.append(nodeName);
          _builder_3.append("/mqtt_handler.ex");
          fsa.generateFile(_builder_3.toString(), 
            this.generateMqttHandler(coord, prefix));
        }
        StringConcatenation _builder_4 = new StringConcatenation();
        _builder_4.append("lib/");
        _builder_4.append(nodeName);
        _builder_4.append("/application.ex");
        fsa.generateFile(_builder_4.toString(), 
          this.generateApplication(system, node, prefix));
      }
    }
  }

  public CharSequence generateApplication(final elixir.of.things.elixirOfThings.System system, final Node node, final String prefix) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("    ");
    _builder.append("defmodule ");
    _builder.append(prefix, "    ");
    _builder.append(".Application do");
    _builder.newLineIfNotEmpty();
    _builder.append("      ");
    _builder.append("use Application");
    _builder.newLine();
    _builder.newLine();
    _builder.append("      ");
    _builder.append("def start(_type, _args) do");
    _builder.newLine();
    _builder.append("        ");
    final Function1<Coordinator, Boolean> _function = (Coordinator it) -> {
      Node _deployedOn = it.getDeployedOn();
      return Boolean.valueOf(Objects.equals(_deployedOn, node));
    };
    final Coordinator coord = IterableExtensions.<Coordinator>findFirst(system.getCoordinators(), _function);
    _builder.newLineIfNotEmpty();
    _builder.append("        ");
    final boolean hasCoord = (coord != null);
    _builder.newLineIfNotEmpty();
    _builder.append("        ");
    _builder.append("tortoise_opts = [");
    _builder.newLine();
    _builder.append("          ");
    _builder.append("client_id: \"");
    String _name = node.getName();
    _builder.append(_name, "          ");
    _builder.append("_");
    {
      if (hasCoord) {
        _builder.append("coordinator");
      } else {
        _builder.append("sensor");
      }
    }
    _builder.append("_node\",");
    _builder.newLineIfNotEmpty();
    _builder.append("          ");
    _builder.append("server: {Tortoise311.Transport.Tcp,");
    _builder.newLine();
    _builder.append("                   ");
    _builder.append("host: \"");
    String _host = system.getBroker().getHost();
    _builder.append(_host, "                   ");
    _builder.append("\",");
    _builder.newLineIfNotEmpty();
    _builder.append("                   ");
    _builder.append("port: ");
    int _port = system.getBroker().getPort();
    _builder.append(_port, "                   ");
    _builder.append("},");
    _builder.newLineIfNotEmpty();
    _builder.append("          ");
    _builder.append("handler: {");
    {
      if (hasCoord) {
        _builder.append(prefix, "          ");
        _builder.append(".MqttHandler");
      } else {
        _builder.append("Tortoise311.Handler.Logger");
      }
    }
    _builder.append(", []}");
    {
      if (hasCoord) {
        _builder.append(",");
        _builder.newLineIfNotEmpty();
        _builder.append("          ");
        _builder.append("subscriptions: [");
        _builder.newLine();
        {
          EList<String> _subscribeTo = coord.getSubscribeTo();
          boolean _hasElements = false;
          for(final String sub : _subscribeTo) {
            if (!_hasElements) {
              _hasElements = true;
            } else {
              _builder.appendImmediate(",", "            ");
            }
            _builder.append("          ");
            _builder.append("  ");
            _builder.append("{\"");
            _builder.append(sub, "            ");
            _builder.append("\", 1}");
            _builder.newLineIfNotEmpty();
          }
        }
        _builder.append("          ");
        _builder.append("]");
        _builder.newLine();
      }
    }
    _builder.append("        ");
    _builder.append("]");
    _builder.newLine();
    _builder.newLine();
    _builder.append("        ");
    _builder.append("children =");
    _builder.newLine();
    _builder.append("          ");
    _builder.append("[{Tortoise311.Connection, tortoise_opts}]");
    _builder.newLine();
    {
      final Function1<Sensor, Boolean> _function_1 = (Sensor it) -> {
        Node _deployedOn = it.getDeployedOn();
        return Boolean.valueOf(Objects.equals(_deployedOn, node));
      };
      Iterable<Sensor> _filter = IterableExtensions.<Sensor>filter(system.getSensors(), _function_1);
      for(final Sensor sensor : _filter) {
        _builder.append("          ");
        _builder.append("++ [");
        _builder.append(prefix, "          ");
        _builder.append(".");
        String _camelCase = this.toCamelCase(sensor.getName());
        _builder.append(_camelCase, "          ");
        _builder.append("]");
        _builder.newLineIfNotEmpty();
      }
    }
    {
      final Function1<Actuator, Boolean> _function_2 = (Actuator it) -> {
        Node _deployedOn = it.getDeployedOn();
        return Boolean.valueOf(Objects.equals(_deployedOn, node));
      };
      Iterable<Actuator> _filter_1 = IterableExtensions.<Actuator>filter(system.getActuators(), _function_2);
      for(final Actuator actuator : _filter_1) {
        _builder.append("          ");
        _builder.append("++ [");
        _builder.append(prefix, "          ");
        _builder.append(".");
        String _camelCase_1 = this.toCamelCase(actuator.getName());
        _builder.append(_camelCase_1, "          ");
        _builder.append("]");
        _builder.newLineIfNotEmpty();
      }
    }
    {
      if (hasCoord) {
        _builder.append("          ");
        _builder.append("++ [");
        _builder.append(prefix, "          ");
        _builder.append(".Coordinator]");
        _builder.newLineIfNotEmpty();
      }
    }
    _builder.newLine();
    _builder.append("        ");
    _builder.append("Supervisor.start_link(children,");
    _builder.newLine();
    _builder.append("          ");
    _builder.append("strategy: :one_for_one,");
    _builder.newLine();
    _builder.append("          ");
    _builder.append("name: ");
    _builder.append(prefix, "          ");
    _builder.append(".Supervisor)");
    _builder.newLineIfNotEmpty();
    _builder.append("      ");
    _builder.append("end");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("end");
    _builder.newLine();
    return _builder;
  }

  public CharSequence generateSensor(final Sensor sensor, final String prefix, final Node node) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("defmodule ");
    _builder.append(prefix);
    _builder.append(".");
    String _camelCase = this.toCamelCase(sensor.getName());
    _builder.append(_camelCase);
    _builder.append(" do");
    _builder.newLineIfNotEmpty();
    _builder.append("  ");
    _builder.append("use GenServer");
    _builder.newLine();
    _builder.append("  ");
    _builder.append("require Logger");
    _builder.newLine();
    _builder.newLine();
    _builder.append("  ");
    _builder.append("def start_link(_),");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("do: GenServer.start_link(__MODULE__, nil, name: __MODULE__)");
    _builder.newLine();
    _builder.newLine();
    {
      String _string = sensor.getType().toString();
      boolean _equals = Objects.equals(_string, "TEMPERATURE");
      if (_equals) {
        _builder.append("  ");
        _builder.append("def init(_) do");
        _builder.newLine();
        _builder.append("  ");
        _builder.append("  ");
        _builder.append(":timer.send_interval(");
        int _value = sensor.getSampleRate().getValue();
        int _multiply = (_value * 1000);
        _builder.append(_multiply, "    ");
        _builder.append(", :read_temp)");
        _builder.newLineIfNotEmpty();
        _builder.append("  ");
        _builder.append("  ");
        _builder.append("{:ok, %{last_state: :low}}");
        _builder.newLine();
        _builder.append("  ");
        _builder.append("end");
        _builder.newLine();
        _builder.newLine();
        _builder.append("  ");
        _builder.append("def handle_info(:read_temp, state) do");
        _builder.newLine();
        _builder.append("  ");
        _builder.append("  ");
        _builder.append("case Path.wildcard(\"/sys/bus/w1/devices/28-*\") do");
        _builder.newLine();
        _builder.append("  ");
        _builder.append("    ");
        _builder.append("[sensor_path | _] ->");
        _builder.newLine();
        _builder.append("  ");
        _builder.append("      ");
        _builder.append("case File.read(Path.join(sensor_path, \"w1_slave\")) do");
        _builder.newLine();
        _builder.append("  ");
        _builder.append("        ");
        _builder.append("{:ok, data} ->");
        _builder.newLine();
        _builder.append("  ");
        _builder.append("          ");
        _builder.append("case Regex.run(~r/t=(-?\\d+)/, data) do");
        _builder.newLine();
        _builder.append("  ");
        _builder.append("            ");
        _builder.append("[_, temp_string] ->");
        _builder.newLine();
        _builder.append("  ");
        _builder.append("              ");
        _builder.append("temp_c = String.to_integer(temp_string) / 1000.0");
        _builder.newLine();
        _builder.append("  ");
        _builder.append("              ");
        _builder.append("current = if temp_c > 25.0, do: :high, else: :low");
        _builder.newLine();
        _builder.newLine();
        _builder.append("  ");
        _builder.append("              ");
        _builder.append("if current != state.last_state do");
        _builder.newLine();
        _builder.append("  ");
        _builder.append("                ");
        _builder.append("topic = if current == :high,");
        _builder.newLine();
        _builder.append("  ");
        _builder.append("                  ");
        _builder.append("do: \"temperature/high\",");
        _builder.newLine();
        _builder.append("  ");
        _builder.append("                  ");
        _builder.append("else: \"temperature/low\"");
        _builder.newLine();
        _builder.append("  ");
        _builder.append("                ");
        _builder.append("Tortoise311.publish(");
        _builder.newLine();
        _builder.append("  ");
        _builder.append("                  ");
        _builder.append("\"");
        String _name = node.getName();
        _builder.append(_name, "                    ");
        _builder.append("_sensor_node\",");
        _builder.newLineIfNotEmpty();
        _builder.append("  ");
        _builder.append("                  ");
        _builder.append("topic,");
        _builder.newLine();
        _builder.append("  ");
        _builder.append("                  ");
        _builder.append("\"#{temp_c}\",");
        _builder.newLine();
        _builder.append("  ");
        _builder.append("                  ");
        _builder.append("qos: 1");
        _builder.newLine();
        _builder.append("  ");
        _builder.append("                ");
        _builder.append(")");
        _builder.newLine();
        _builder.append("  ");
        _builder.append("              ");
        _builder.append("end");
        _builder.newLine();
        _builder.append("  ");
        _builder.append("              ");
        _builder.append("{:noreply, %{last_state: current}}");
        _builder.newLine();
        _builder.newLine();
        _builder.append("  ");
        _builder.append("            ");
        _builder.append("_ -> {:noreply, state}");
        _builder.newLine();
        _builder.append("  ");
        _builder.append("          ");
        _builder.append("end");
        _builder.newLine();
        _builder.append("  ");
        _builder.append("        ");
        _builder.append("_ -> {:noreply, state}");
        _builder.newLine();
        _builder.append("  ");
        _builder.append("      ");
        _builder.append("end");
        _builder.newLine();
        _builder.append("  ");
        _builder.append("    ");
        _builder.append("[] -> {:noreply, state}");
        _builder.newLine();
        _builder.append("  ");
        _builder.append("  ");
        _builder.append("end");
        _builder.newLine();
        _builder.append("  ");
        _builder.append("end");
        _builder.newLine();
        _builder.newLine();
      } else {
        String _string_1 = sensor.getType().toString();
        boolean _equals_1 = Objects.equals(_string_1, "MOTION");
        if (_equals_1) {
          _builder.append("  ");
          _builder.append("def init(_) do");
          _builder.newLine();
          _builder.append("  ");
          _builder.append("  ");
          _builder.append("{:ok, gpio} = Circuits.GPIO.open(");
          int _gpioPin = sensor.getGpioPin();
          _builder.append(_gpioPin, "    ");
          _builder.append(", :input)");
          _builder.newLineIfNotEmpty();
          _builder.append("  ");
          _builder.append("  ");
          _builder.append("Circuits.GPIO.set_interrupts(gpio, :both)");
          _builder.newLine();
          _builder.append("  ");
          _builder.append("  ");
          _builder.append("{:ok, gpio}");
          _builder.newLine();
          _builder.append("  ");
          _builder.append("end");
          _builder.newLine();
          _builder.newLine();
          _builder.append("  ");
          _builder.append("def handle_info({:circuits_gpio, ");
          int _gpioPin_1 = sensor.getGpioPin();
          _builder.append(_gpioPin_1, "  ");
          _builder.append(", _timestamp, 1}, state) do");
          _builder.newLineIfNotEmpty();
          _builder.append("  ");
          _builder.append("  ");
          _builder.append("Logger.warning(\"Motion detected! Publishing.\")");
          _builder.newLine();
          _builder.append("  ");
          _builder.append("  ");
          _builder.append("Tortoise311.publish(");
          _builder.newLine();
          _builder.append("  ");
          _builder.append("    ");
          _builder.append("\"");
          String _name_1 = node.getName();
          _builder.append(_name_1, "      ");
          _builder.append("_sensor_node\",");
          _builder.newLineIfNotEmpty();
          _builder.append("  ");
          _builder.append("    ");
          _builder.append("\"motion/detected\",");
          _builder.newLine();
          _builder.append("  ");
          _builder.append("    ");
          _builder.append("\"true\",");
          _builder.newLine();
          _builder.append("  ");
          _builder.append("    ");
          _builder.append("qos: 1");
          _builder.newLine();
          _builder.append("  ");
          _builder.append("  ");
          _builder.append(")");
          _builder.newLine();
          _builder.append("  ");
          _builder.append("  ");
          _builder.append("{:noreply, state}");
          _builder.newLine();
          _builder.append("  ");
          _builder.append("end");
          _builder.newLine();
          _builder.newLine();
          _builder.append("  ");
          _builder.append("def handle_info({:circuits_gpio, ");
          int _gpioPin_2 = sensor.getGpioPin();
          _builder.append(_gpioPin_2, "  ");
          _builder.append(", _timestamp, 0}, state) do");
          _builder.newLineIfNotEmpty();
          _builder.append("  ");
          _builder.append("  ");
          _builder.append("Logger.info(\"Motion cleared. Publishing still.\")");
          _builder.newLine();
          _builder.append("  ");
          _builder.append("  ");
          _builder.append("Tortoise311.publish(");
          _builder.newLine();
          _builder.append("  ");
          _builder.append("    ");
          _builder.append("\"");
          String _name_2 = node.getName();
          _builder.append(_name_2, "      ");
          _builder.append("_sensor_node\",");
          _builder.newLineIfNotEmpty();
          _builder.append("  ");
          _builder.append("    ");
          _builder.append("\"motion/still\",");
          _builder.newLine();
          _builder.append("  ");
          _builder.append("    ");
          _builder.append("\"false\",");
          _builder.newLine();
          _builder.append("  ");
          _builder.append("    ");
          _builder.append("qos: 1");
          _builder.newLine();
          _builder.append("  ");
          _builder.append("  ");
          _builder.append(")");
          _builder.newLine();
          _builder.append("  ");
          _builder.append("  ");
          _builder.append("{:noreply, state}");
          _builder.newLine();
          _builder.append("  ");
          _builder.append("end");
          _builder.newLine();
        }
      }
    }
    _builder.newLine();
    _builder.append("  ");
    _builder.append("def handle_info(_message, state), do: {:noreply, state}");
    _builder.newLine();
    _builder.append("end");
    _builder.newLine();
    return _builder;
  }

  public CharSequence generateActuator(final Actuator actuator, final String prefix) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("defmodule ");
    _builder.append(prefix);
    _builder.append(".");
    String _camelCase = this.toCamelCase(actuator.getName());
    _builder.append(_camelCase);
    _builder.append(" do");
    _builder.newLineIfNotEmpty();
    _builder.append("  ");
    _builder.append("use GenServer");
    _builder.newLine();
    _builder.newLine();
    _builder.append("  ");
    _builder.append("def turn_on(),  do: GenServer.cast(__MODULE__, :turn_on)");
    _builder.newLine();
    _builder.append("  ");
    _builder.append("def turn_off(), do: GenServer.cast(__MODULE__, :turn_off)");
    _builder.newLine();
    _builder.newLine();
    _builder.append("  ");
    _builder.append("def start_link(_),");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("do: GenServer.start_link(__MODULE__, nil, name: __MODULE__)");
    _builder.newLine();
    _builder.newLine();
    _builder.append("  ");
    _builder.append("def init(_) do");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("{:ok, gpio} = Circuits.GPIO.open(");
    int _gpioPin = actuator.getGpioPin();
    _builder.append(_gpioPin, "    ");
    _builder.append(", :output)");
    _builder.newLineIfNotEmpty();
    _builder.append("    ");
    _builder.append("Circuits.GPIO.write(gpio, 0)");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("{:ok, gpio}");
    _builder.newLine();
    _builder.append("  ");
    _builder.append("end");
    _builder.newLine();
    _builder.newLine();
    _builder.append("  ");
    _builder.append("def handle_cast(:turn_on, gpio) do");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("Circuits.GPIO.write(gpio, 1)");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("{:noreply, gpio}");
    _builder.newLine();
    _builder.append("  ");
    _builder.append("end");
    _builder.newLine();
    _builder.newLine();
    _builder.append("  ");
    _builder.append("def handle_cast(:turn_off, gpio) do");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("Circuits.GPIO.write(gpio, 0)");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("{:noreply, gpio}");
    _builder.newLine();
    _builder.append("  ");
    _builder.append("end");
    _builder.newLine();
    _builder.append("end");
    _builder.newLine();
    return _builder;
  }

  public CharSequence generateMqttHandler(final Coordinator coord, final String prefix) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("defmodule ");
    _builder.append(prefix);
    _builder.append(".MqttHandler do");
    _builder.newLineIfNotEmpty();
    _builder.append("  ");
    _builder.append("use Tortoise311.Handler");
    _builder.newLine();
    _builder.append("  ");
    _builder.append("require Logger");
    _builder.newLine();
    _builder.newLine();
    _builder.append("  ");
    _builder.append("def init(args), do: {:ok, args}");
    _builder.newLine();
    _builder.append("  ");
    _builder.append("def connection(_status, state), do: {:ok, state}");
    _builder.newLine();
    _builder.newLine();
    {
      EList<String> _subscribeTo = coord.getSubscribeTo();
      for(final String topic : _subscribeTo) {
        _builder.append("  ");
        final String[] parts = topic.split("/");
        _builder.newLineIfNotEmpty();
        _builder.append("  ");
        _builder.append("def handle_message([\"");
        String _get = parts[0];
        _builder.append(_get, "  ");
        _builder.append("\", \"");
        String _get_1 = parts[1];
        _builder.append(_get_1, "  ");
        _builder.append("\"], _payload, state) do");
        _builder.newLineIfNotEmpty();
        _builder.append("  ");
        _builder.append("  ");
        _builder.append(prefix, "    ");
        _builder.append(".Coordinator.update_state(");
        _builder.newLineIfNotEmpty();
        _builder.append("  ");
        _builder.append("    ");
        _builder.append(":");
        String _get_2 = parts[0];
        _builder.append(_get_2, "      ");
        _builder.append(",");
        _builder.newLineIfNotEmpty();
        _builder.append("  ");
        _builder.append("    ");
        _builder.append(":");
        String _get_3 = parts[1];
        _builder.append(_get_3, "      ");
        _builder.newLineIfNotEmpty();
        _builder.append("  ");
        _builder.append("  ");
        _builder.append(")");
        _builder.newLine();
        _builder.append("  ");
        _builder.append("  ");
        _builder.append("{:ok, state}");
        _builder.newLine();
        _builder.append("  ");
        _builder.append("end");
        _builder.newLine();
        _builder.newLine();
      }
    }
    _builder.append("  ");
    _builder.append("def subscription(_status, _topic_filter, state), do: {:ok, state}");
    _builder.newLine();
    _builder.append("  ");
    _builder.append("def terminate(_reason, _state), do: :ok");
    _builder.newLine();
    _builder.append("end");
    _builder.newLine();
    return _builder;
  }

  public CharSequence generateCoordinator(final Coordinator coord, final String prefix, final elixir.of.things.elixirOfThings.System system) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("defmodule ");
    _builder.append(prefix);
    _builder.append(".Coordinator do");
    _builder.newLineIfNotEmpty();
    _builder.append("  ");
    _builder.append("use GenServer");
    _builder.newLine();
    _builder.append("  ");
    _builder.append("require Logger");
    _builder.newLine();
    _builder.newLine();
    _builder.append("  ");
    _builder.append("def update_state(sensor, value) do");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("GenServer.cast(__MODULE__, {:update, sensor, value})");
    _builder.newLine();
    _builder.append("  ");
    _builder.append("end");
    _builder.newLine();
    _builder.newLine();
    _builder.append("  ");
    _builder.append("def start_link(_),");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("do: GenServer.start_link(__MODULE__, nil, name: __MODULE__)");
    _builder.newLine();
    _builder.newLine();
    _builder.append("  ");
    _builder.append("def init(_) do");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("{:ok, %{temperature: :low, motion: :still}}");
    _builder.newLine();
    _builder.append("  ");
    _builder.append("end");
    _builder.newLine();
    _builder.newLine();
    _builder.append("  ");
    _builder.append("def handle_cast({:update, sensor, value}, state) do");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("new_state = Map.put(state, sensor, value)");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("evaluate_rules(new_state)");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("{:noreply, new_state}");
    _builder.newLine();
    _builder.append("  ");
    _builder.append("end");
    _builder.newLine();
    _builder.newLine();
    _builder.append("  ");
    _builder.append("defp evaluate_rules(state) do");
    _builder.newLine();
    {
      EList<Rule> _rules = coord.getRules();
      for(final Rule rule : _rules) {
        _builder.append("    ");
        _builder.append("# DSL Rule: ");
        String _name = rule.getName();
        _builder.append(_name, "    ");
        _builder.newLineIfNotEmpty();
        _builder.append("    ");
        _builder.append("if ");
        {
          EList<TopicPath> _topics = rule.getCondition().getTopics();
          boolean _hasElements = false;
          for(final TopicPath topic : _topics) {
            if (!_hasElements) {
              _hasElements = true;
            } else {
              _builder.appendImmediate(" and ", "    ");
            }
            _builder.append("state.");
            String _get = topic.getSegments().get(0);
            _builder.append(_get, "    ");
            _builder.append(" == :");
            String _get_1 = topic.getSegments().get(1);
            _builder.append(_get_1, "    ");
          }
        }
        _builder.append(" do");
        _builder.newLineIfNotEmpty();
        _builder.append("    ");
        _builder.append("  ");
        _builder.append("Logger.error(\"ALERT: ");
        String _message = rule.getActions().get(0).getMessage();
        _builder.append(_message, "      ");
        _builder.append("\")");
        _builder.newLineIfNotEmpty();
        _builder.append("    ");
        _builder.append("  ");
        _builder.append("Tortoise311.publish(");
        _builder.newLine();
        _builder.append("    ");
        _builder.append("    ");
        _builder.append("\"");
        String _name_1 = coord.getDeployedOn().getName();
        _builder.append(_name_1, "        ");
        _builder.append("_coordinator_node\",");
        _builder.newLineIfNotEmpty();
        _builder.append("    ");
        _builder.append("    ");
        _builder.append("\"alerts/warning\",");
        _builder.newLine();
        _builder.append("    ");
        _builder.append("    ");
        _builder.append("~s({\"message\": \"");
        String _message_1 = rule.getActions().get(0).getMessage();
        _builder.append(_message_1, "        ");
        _builder.append("\"}),");
        _builder.newLineIfNotEmpty();
        _builder.append("    ");
        _builder.append("    ");
        _builder.append("qos: 1");
        _builder.newLine();
        _builder.append("    ");
        _builder.append("  ");
        _builder.append(")");
        _builder.newLine();
        _builder.append("    ");
        _builder.append("end");
        _builder.newLine();
      }
    }
    _builder.newLine();
    {
      final Function1<Actuator, Boolean> _function = (Actuator it) -> {
        Node _deployedOn = it.getDeployedOn();
        Node _deployedOn_1 = coord.getDeployedOn();
        return Boolean.valueOf(Objects.equals(_deployedOn, _deployedOn_1));
      };
      Iterable<Actuator> _filter = IterableExtensions.<Actuator>filter(system.getActuators(), _function);
      for(final Actuator actuator : _filter) {
        {
          String _string = actuator.getType().toString();
          boolean _equals = Objects.equals(_string, "LED");
          if (_equals) {
            _builder.append("    ");
            _builder.append("if state.temperature == :high,");
            _builder.newLine();
            _builder.append("    ");
            _builder.append("  ");
            _builder.append("do: ");
            _builder.append(prefix, "      ");
            _builder.append(".");
            String _camelCase = this.toCamelCase(actuator.getName());
            _builder.append(_camelCase, "      ");
            _builder.append(".turn_on(),");
            _builder.newLineIfNotEmpty();
            _builder.append("    ");
            _builder.append("  ");
            _builder.append("else: ");
            _builder.append(prefix, "      ");
            _builder.append(".");
            String _camelCase_1 = this.toCamelCase(actuator.getName());
            _builder.append(_camelCase_1, "      ");
            _builder.append(".turn_off()");
            _builder.newLineIfNotEmpty();
          } else {
            String _string_1 = actuator.getType().toString();
            boolean _equals_1 = Objects.equals(_string_1, "BUZZER");
            if (_equals_1) {
              _builder.append("    ");
              _builder.append("if state.motion == :detected,");
              _builder.newLine();
              _builder.append("    ");
              _builder.append("  ");
              _builder.append("do: ");
              _builder.append(prefix, "      ");
              _builder.append(".");
              String _camelCase_2 = this.toCamelCase(actuator.getName());
              _builder.append(_camelCase_2, "      ");
              _builder.append(".turn_on(),");
              _builder.newLineIfNotEmpty();
              _builder.append("    ");
              _builder.append("  ");
              _builder.append("else: ");
              _builder.append(prefix, "      ");
              _builder.append(".");
              String _camelCase_3 = this.toCamelCase(actuator.getName());
              _builder.append(_camelCase_3, "      ");
              _builder.append(".turn_off()");
              _builder.newLineIfNotEmpty();
            }
          }
        }
      }
    }
    _builder.append("  ");
    _builder.append("end");
    _builder.newLine();
    _builder.append("end");
    _builder.newLine();
    return _builder;
  }
}

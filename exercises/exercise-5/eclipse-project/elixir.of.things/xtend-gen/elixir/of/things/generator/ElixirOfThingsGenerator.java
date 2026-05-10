package elixir.of.things.generator;

import elixir.of.things.elixirOfThings.Actuator;
import elixir.of.things.elixirOfThings.ActuatorType;
import elixir.of.things.elixirOfThings.BoolAndExpr;
import elixir.of.things.elixirOfThings.BoolExpr;
import elixir.of.things.elixirOfThings.BoolNotExpr;
import elixir.of.things.elixirOfThings.BoolOrExpr;
import elixir.of.things.elixirOfThings.Coordinator;
import elixir.of.things.elixirOfThings.Node;
import elixir.of.things.elixirOfThings.NumAddExpr;
import elixir.of.things.elixirOfThings.NumDivExpr;
import elixir.of.things.elixirOfThings.NumExpr;
import elixir.of.things.elixirOfThings.NumLiteral;
import elixir.of.things.elixirOfThings.NumMulExpr;
import elixir.of.things.elixirOfThings.NumSubExpr;
import elixir.of.things.elixirOfThings.OnMessage;
import elixir.of.things.elixirOfThings.Operator;
import elixir.of.things.elixirOfThings.Rule;
import elixir.of.things.elixirOfThings.RuleAction;
import elixir.of.things.elixirOfThings.SampleRate;
import elixir.of.things.elixirOfThings.Sensor;
import elixir.of.things.elixirOfThings.State;
import elixir.of.things.elixirOfThings.TimeUnit;
import elixir.of.things.elixirOfThings.Topic;
import elixir.of.things.elixirOfThings.TopicRef;
import elixir.of.things.elixirOfThings.Trigger;
import elixir.of.things.elixirOfThings.TriggerAction;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.xtend2.lib.StringConcatenation;
import org.eclipse.xtext.generator.AbstractGenerator;
import org.eclipse.xtext.generator.IFileSystemAccess2;
import org.eclipse.xtext.generator.IGeneratorContext;
import org.eclipse.xtext.xbase.lib.CollectionLiterals;
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

  public String toElixirOp(final Operator op) {
    String _switchResult = null;
    if (op != null) {
      switch (op) {
        case GREATER_THAN:
          _switchResult = ">";
          break;
        case LESS_THAN:
          _switchResult = "<";
          break;
        case GREATER_EQ:
          _switchResult = ">=";
          break;
        case LESS_EQ:
          _switchResult = "<=";
          break;
        case EQUALS:
          _switchResult = "==";
          break;
        default:
          _switchResult = "==";
          break;
      }
    } else {
      _switchResult = "==";
    }
    return _switchResult;
  }

  public String topicStr(final Topic t) {
    return t.getTopicString();
  }

  public int evalNumExpr(final NumExpr expr) {
    int _xifexpression = (int) 0;
    if ((expr instanceof NumLiteral)) {
      _xifexpression = ((NumLiteral)expr).getValue();
    } else {
      int _xifexpression_1 = (int) 0;
      if ((expr instanceof NumAddExpr)) {
        int _evalNumExpr = this.evalNumExpr(((NumAddExpr)expr).getLeft());
        int _evalNumExpr_1 = this.evalNumExpr(((NumAddExpr)expr).getRight());
        _xifexpression_1 = (_evalNumExpr + _evalNumExpr_1);
      } else {
        int _xifexpression_2 = (int) 0;
        if ((expr instanceof NumSubExpr)) {
          int _evalNumExpr_2 = this.evalNumExpr(((NumSubExpr)expr).getLeft());
          int _evalNumExpr_3 = this.evalNumExpr(((NumSubExpr)expr).getRight());
          _xifexpression_2 = (_evalNumExpr_2 - _evalNumExpr_3);
        } else {
          int _xifexpression_3 = (int) 0;
          if ((expr instanceof NumMulExpr)) {
            int _evalNumExpr_4 = this.evalNumExpr(((NumMulExpr)expr).getLeft());
            int _evalNumExpr_5 = this.evalNumExpr(((NumMulExpr)expr).getRight());
            _xifexpression_3 = (_evalNumExpr_4 * _evalNumExpr_5);
          } else {
            int _xifexpression_4 = (int) 0;
            if ((expr instanceof NumDivExpr)) {
              int _evalNumExpr_6 = this.evalNumExpr(((NumDivExpr)expr).getLeft());
              int _evalNumExpr_7 = this.evalNumExpr(((NumDivExpr)expr).getRight());
              _xifexpression_4 = (_evalNumExpr_6 / _evalNumExpr_7);
            } else {
              _xifexpression_4 = 0;
            }
            _xifexpression_3 = _xifexpression_4;
          }
          _xifexpression_2 = _xifexpression_3;
        }
        _xifexpression_1 = _xifexpression_2;
      }
      _xifexpression = _xifexpression_1;
    }
    return _xifexpression;
  }

  public int sampleRateToMillis(final SampleRate sr) {
    int _xblockexpression = (int) 0;
    {
      final int v = this.evalNumExpr(sr.getValue());
      int _switchResult = (int) 0;
      String _string = sr.getUnit().toString();
      if (_string != null) {
        switch (_string) {
          case "SEC":
            _switchResult = (v * 1000);
            break;
          case "MIN":
            _switchResult = (v * 60000);
            break;
          case "MS":
            _switchResult = v;
            break;
          default:
            _switchResult = (v * 1000);
            break;
        }
      } else {
        _switchResult = (v * 1000);
      }
      _xblockexpression = _switchResult;
    }
    return _xblockexpression;
  }

  public int durationToMillis(final SampleRate sr) {
    return this.sampleRateToMillis(sr);
  }

  public String generateBoolExpr(final BoolExpr expr) {
    String _xifexpression = null;
    if ((expr instanceof BoolOrExpr)) {
      StringConcatenation _builder = new StringConcatenation();
      _builder.append("(");
      String _generateBoolExpr = this.generateBoolExpr(((BoolOrExpr)expr).getLeft());
      _builder.append(_generateBoolExpr);
      _builder.append(" or ");
      String _generateBoolExpr_1 = this.generateBoolExpr(((BoolOrExpr)expr).getRight());
      _builder.append(_generateBoolExpr_1);
      _builder.append(")");
      _xifexpression = _builder.toString();
    } else {
      String _xifexpression_1 = null;
      if ((expr instanceof BoolAndExpr)) {
        StringConcatenation _builder_1 = new StringConcatenation();
        _builder_1.append("(");
        String _generateBoolExpr_2 = this.generateBoolExpr(((BoolAndExpr)expr).getLeft());
        _builder_1.append(_generateBoolExpr_2);
        _builder_1.append(" and ");
        String _generateBoolExpr_3 = this.generateBoolExpr(((BoolAndExpr)expr).getRight());
        _builder_1.append(_generateBoolExpr_3);
        _builder_1.append(")");
        _xifexpression_1 = _builder_1.toString();
      } else {
        String _xifexpression_2 = null;
        if ((expr instanceof BoolNotExpr)) {
          StringConcatenation _builder_2 = new StringConcatenation();
          _builder_2.append("not (");
          String _generateBoolExpr_4 = this.generateBoolExpr(((BoolNotExpr)expr).getOperand());
          _builder_2.append(_generateBoolExpr_4);
          _builder_2.append(")");
          _xifexpression_2 = _builder_2.toString();
        } else {
          String _xifexpression_3 = null;
          if ((expr instanceof TopicRef)) {
            String _xblockexpression = null;
            {
              final String[] parts = this.topicStr(((TopicRef)expr).getTopic()).split("/");
              StringConcatenation _builder_3 = new StringConcatenation();
              _builder_3.append("state.");
              String _get = parts[0];
              _builder_3.append(_get);
              _builder_3.append(" == :");
              String _get_1 = parts[1];
              _builder_3.append(_get_1);
              _xblockexpression = _builder_3.toString();
            }
            _xifexpression_3 = _xblockexpression;
          } else {
            StringConcatenation _builder_3 = new StringConcatenation();
            _builder_3.append("true");
            _xifexpression_3 = _builder_3.toString();
          }
          _xifexpression_2 = _xifexpression_3;
        }
        _xifexpression_1 = _xifexpression_2;
      }
      _xifexpression = _xifexpression_1;
    }
    return _xifexpression;
  }

  public Set<String> uniqueStateKeys(final Coordinator coord) {
    final Function1<Topic, String> _function = (Topic it) -> {
      return this.topicStr(it).split("/")[0];
    };
    return IterableExtensions.<String>toSet(ListExtensions.<Topic, String>map(coord.getSubscribeTo(), _function));
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
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("dashboard/topology.json");
    fsa.generateFile(_builder.toString(), 
      this.generateTopologyJson(system));
    StringConcatenation _builder_1 = new StringConcatenation();
    _builder_1.append("dashboard/topics.json");
    fsa.generateFile(_builder_1.toString(), 
      this.generateTopicsJson(system));
  }

  public CharSequence generateApplication(final elixir.of.things.elixirOfThings.System system, final Node node, final String prefix) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("defmodule ");
    _builder.append(prefix);
    _builder.append(".Application do");
    _builder.newLineIfNotEmpty();
    _builder.append("  ");
    _builder.append("use Application");
    _builder.newLine();
    _builder.newLine();
    _builder.append("  ");
    _builder.append("def start(_type, _args) do");
    _builder.newLine();
    _builder.append("    ");
    final Function1<Coordinator, Boolean> _function = (Coordinator it) -> {
      Node _deployedOn = it.getDeployedOn();
      return Boolean.valueOf(Objects.equals(_deployedOn, node));
    };
    final Coordinator coord = IterableExtensions.<Coordinator>findFirst(system.getCoordinators(), _function);
    _builder.newLineIfNotEmpty();
    _builder.append("    ");
    final boolean hasCoord = (coord != null);
    _builder.newLineIfNotEmpty();
    _builder.append("    ");
    _builder.append("tortoise_opts = [");
    _builder.newLine();
    _builder.append("      ");
    _builder.append("client_id: \"");
    String _name = node.getName();
    _builder.append(_name, "      ");
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
    _builder.append("      ");
    _builder.append("server: {Tortoise311.Transport.Tcp,");
    _builder.newLine();
    _builder.append("               ");
    _builder.append("host: \"");
    String _host = system.getBroker().getHost();
    _builder.append(_host, "               ");
    _builder.append("\",");
    _builder.newLineIfNotEmpty();
    _builder.append("               ");
    _builder.append("port: ");
    int _port = system.getBroker().getPort();
    _builder.append(_port, "               ");
    _builder.append("},");
    _builder.newLineIfNotEmpty();
    _builder.append("      ");
    _builder.append("handler: {");
    {
      if (hasCoord) {
        _builder.append(prefix, "      ");
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
        _builder.append("      ");
        _builder.append("subscriptions: [");
        _builder.newLine();
        {
          EList<Topic> _subscribeTo = coord.getSubscribeTo();
          boolean _hasElements = false;
          for(final Topic sub : _subscribeTo) {
            if (!_hasElements) {
              _hasElements = true;
            } else {
              _builder.appendImmediate(",", "        ");
            }
            _builder.append("      ");
            _builder.append("  ");
            _builder.append("{\"");
            String _picStr = this.topicStr(sub);
            _builder.append(_picStr, "        ");
            _builder.append("\", 1}");
            _builder.newLineIfNotEmpty();
          }
        }
        _builder.append("      ");
        _builder.append("]");
        _builder.newLine();
      }
    }
    _builder.append("    ");
    _builder.append("]");
    _builder.newLine();
    _builder.newLine();
    _builder.append("    ");
    _builder.append("children =");
    _builder.newLine();
    _builder.append("      ");
    _builder.append("[{Tortoise311.Connection, tortoise_opts}]");
    _builder.newLine();
    {
      final Function1<Sensor, Boolean> _function_1 = (Sensor it) -> {
        Node _deployedOn = it.getDeployedOn();
        return Boolean.valueOf(Objects.equals(_deployedOn, node));
      };
      Iterable<Sensor> _filter = IterableExtensions.<Sensor>filter(system.getSensors(), _function_1);
      for(final Sensor sensor : _filter) {
        _builder.append("      ");
        _builder.append("++ [");
        _builder.append(prefix, "      ");
        _builder.append(".");
        String _camelCase = this.toCamelCase(sensor.getName());
        _builder.append(_camelCase, "      ");
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
        _builder.append("      ");
        _builder.append("++ [");
        _builder.append(prefix, "      ");
        _builder.append(".");
        String _camelCase_1 = this.toCamelCase(actuator.getName());
        _builder.append(_camelCase_1, "      ");
        _builder.append("]");
        _builder.newLineIfNotEmpty();
      }
    }
    {
      if (hasCoord) {
        _builder.append("      ");
        _builder.append("++ [");
        _builder.append(prefix, "      ");
        _builder.append(".Coordinator]");
        _builder.newLineIfNotEmpty();
      }
    }
    _builder.newLine();
    _builder.append("    ");
    _builder.append("Supervisor.start_link(children,");
    _builder.newLine();
    _builder.append("      ");
    _builder.append("strategy: :one_for_one,");
    _builder.newLine();
    _builder.append("      ");
    _builder.append("name: ");
    _builder.append(prefix, "      ");
    _builder.append(".Supervisor)");
    _builder.newLineIfNotEmpty();
    _builder.append("  ");
    _builder.append("end");
    _builder.newLine();
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
      boolean _equals = Objects.equals(_string, "TEMP_DS18B20");
      if (_equals) {
        _builder.append("  ");
        _builder.append("# DS18B20 Temperature Sensor (1-Wire)");
        _builder.newLine();
        _builder.append("  ");
        _builder.append("# sampleRate: ");
        int _evalNumExpr = this.evalNumExpr(sensor.getSampleRate().getValue());
        _builder.append(_evalNumExpr, "  ");
        _builder.append(" ");
        TimeUnit _unit = sensor.getSampleRate().getUnit();
        _builder.append(_unit, "  ");
        _builder.append(" = ");
        int _sampleRateToMillis = this.sampleRateToMillis(sensor.getSampleRate());
        _builder.append(_sampleRateToMillis, "  ");
        _builder.append(" ms");
        _builder.newLineIfNotEmpty();
        _builder.append("  ");
        _builder.append("# GPIO ");
        int _gpioPin = sensor.getGpioPin();
        _builder.append(_gpioPin, "  ");
        _builder.append(" ");
        _builder.newLineIfNotEmpty();
        _builder.newLine();
        _builder.append("  ");
        _builder.append("def init(_) do");
        _builder.newLine();
        _builder.append("  ");
        _builder.append("  ");
        _builder.append(":timer.send_interval(");
        int _sampleRateToMillis_1 = this.sampleRateToMillis(sensor.getSampleRate());
        _builder.append(_sampleRateToMillis_1, "    ");
        _builder.append(", :read_sensor)");
        _builder.newLineIfNotEmpty();
        _builder.append("  ");
        _builder.append("  ");
        _builder.append("{:ok, %{last_value: nil}}");
        _builder.newLine();
        _builder.append("  ");
        _builder.append("end");
        _builder.newLine();
        _builder.newLine();
        _builder.append("  ");
        _builder.append("def handle_info(:read_sensor, state) do");
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
        _builder.append("[_, raw] ->");
        _builder.newLine();
        _builder.append("  ");
        _builder.append("              ");
        _builder.append("value = String.to_integer(raw) / 1000.0");
        _builder.newLine();
        _builder.append("  ");
        _builder.append("              ");
        _builder.append("Logger.debug(\"");
        String _name = sensor.getName();
        _builder.append(_name, "                ");
        _builder.append(": #{value}°C\")");
        _builder.newLineIfNotEmpty();
        {
          EList<Trigger> _triggers = sensor.getTriggers();
          for(final Trigger trigger : _triggers) {
            _builder.append("  ");
            _builder.append("              ");
            _builder.append("# Trigger ");
            String _name_1 = trigger.getName();
            _builder.append(_name_1, "                ");
            _builder.append(": when value ");
            String _elixirOp = this.toElixirOp(trigger.getCondition().getOperator());
            _builder.append(_elixirOp, "                ");
            _builder.append(" ");
            int _evalNumExpr_1 = this.evalNumExpr(trigger.getCondition().getRight());
            _builder.append(_evalNumExpr_1, "                ");
            _builder.newLineIfNotEmpty();
            _builder.append("  ");
            _builder.append("              ");
            _builder.append("if value ");
            String _elixirOp_1 = this.toElixirOp(trigger.getCondition().getOperator());
            _builder.append(_elixirOp_1, "                ");
            _builder.append(" ");
            int _evalNumExpr_2 = this.evalNumExpr(trigger.getCondition().getRight());
            _builder.append(_evalNumExpr_2, "                ");
            _builder.append(" do");
            _builder.newLineIfNotEmpty();
            {
              EList<TriggerAction> _actions = trigger.getActions();
              for(final TriggerAction action : _actions) {
                _builder.append("  ");
                _builder.append("              ");
                _builder.append("  ");
                _builder.append("Tortoise311.publish(");
                _builder.newLine();
                _builder.append("  ");
                _builder.append("              ");
                _builder.append("  ");
                _builder.append("  ");
                _builder.append("\"");
                String _name_2 = node.getName();
                _builder.append(_name_2, "                    ");
                _builder.append("_sensor_node\",");
                _builder.newLineIfNotEmpty();
                _builder.append("  ");
                _builder.append("              ");
                _builder.append("  ");
                _builder.append("  ");
                _builder.append("\"");
                String _picStr = this.topicStr(action.getTopic());
                _builder.append(_picStr, "                    ");
                _builder.append("\",");
                _builder.newLineIfNotEmpty();
                _builder.append("  ");
                _builder.append("              ");
                _builder.append("  ");
                _builder.append("  ");
                _builder.append("\"#{value}\",");
                _builder.newLine();
                _builder.append("  ");
                _builder.append("              ");
                _builder.append("  ");
                _builder.append("  ");
                _builder.append("qos: 1");
                _builder.newLine();
                _builder.append("  ");
                _builder.append("              ");
                _builder.append("  ");
                _builder.append(")");
                _builder.newLine();
              }
            }
            _builder.append("  ");
            _builder.append("              ");
            _builder.append("end");
            _builder.newLine();
          }
        }
        _builder.append("  ");
        _builder.append("              ");
        _builder.append("{:noreply, %{last_value: value}}");
        _builder.newLine();
        _builder.append("  ");
        _builder.append("            ");
        _builder.append("_ ->");
        _builder.newLine();
        _builder.append("  ");
        _builder.append("              ");
        _builder.append("Logger.warning(\"");
        String _name_3 = sensor.getName();
        _builder.append(_name_3, "                ");
        _builder.append(": failed to parse data\")");
        _builder.newLineIfNotEmpty();
        _builder.append("  ");
        _builder.append("              ");
        _builder.append("{:noreply, state}");
        _builder.newLine();
        _builder.append("  ");
        _builder.append("          ");
        _builder.append("end");
        _builder.newLine();
        _builder.append("  ");
        _builder.append("        ");
        _builder.append("{:error, reason} ->");
        _builder.newLine();
        _builder.append("  ");
        _builder.append("          ");
        _builder.append("Logger.warning(\"");
        String _name_4 = sensor.getName();
        _builder.append(_name_4, "            ");
        _builder.append(": read error #{inspect(reason)}\")");
        _builder.newLineIfNotEmpty();
        _builder.append("  ");
        _builder.append("          ");
        _builder.append("{:noreply, state}");
        _builder.newLine();
        _builder.append("  ");
        _builder.append("      ");
        _builder.append("end");
        _builder.newLine();
        _builder.append("  ");
        _builder.append("    ");
        _builder.append("[] ->");
        _builder.newLine();
        _builder.append("  ");
        _builder.append("      ");
        _builder.append("Logger.warning(\"");
        String _name_5 = sensor.getName();
        _builder.append(_name_5, "        ");
        _builder.append(": no 1-Wire device found\")");
        _builder.newLineIfNotEmpty();
        _builder.append("  ");
        _builder.append("      ");
        _builder.append("{:noreply, state}");
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
        boolean _equals_1 = Objects.equals(_string_1, "TEMP_DHT22");
        if (_equals_1) {
          _builder.append("  ");
          _builder.append("# DHT22 Temperature Sensor");
          _builder.newLine();
          _builder.append("  ");
          _builder.append("# sampleRate: ");
          int _evalNumExpr_3 = this.evalNumExpr(sensor.getSampleRate().getValue());
          _builder.append(_evalNumExpr_3, "  ");
          _builder.append(" ");
          TimeUnit _unit_1 = sensor.getSampleRate().getUnit();
          _builder.append(_unit_1, "  ");
          _builder.append(" = ");
          int _sampleRateToMillis_2 = this.sampleRateToMillis(sensor.getSampleRate());
          _builder.append(_sampleRateToMillis_2, "  ");
          _builder.append(" ms");
          _builder.newLineIfNotEmpty();
          _builder.append("  ");
          _builder.append("# GPIO ");
          int _gpioPin_1 = sensor.getGpioPin();
          _builder.append(_gpioPin_1, "  ");
          _builder.newLineIfNotEmpty();
          _builder.newLine();
          _builder.append("  ");
          _builder.append("def init(_) do");
          _builder.newLine();
          _builder.append("  ");
          _builder.append("  ");
          _builder.append(":timer.send_interval(");
          int _sampleRateToMillis_3 = this.sampleRateToMillis(sensor.getSampleRate());
          _builder.append(_sampleRateToMillis_3, "    ");
          _builder.append(", :read_sensor)");
          _builder.newLineIfNotEmpty();
          _builder.append("  ");
          _builder.append("  ");
          _builder.append("{:ok, %{last_value: nil}}");
          _builder.newLine();
          _builder.append("  ");
          _builder.append("end");
          _builder.newLine();
          _builder.newLine();
          _builder.append("  ");
          _builder.append("def handle_info(:read_sensor, state) do");
          _builder.newLine();
          _builder.append("  ");
          _builder.append("  ");
          _builder.append("{:ok, gpio} = Circuits.GPIO.open(");
          int _gpioPin_2 = sensor.getGpioPin();
          _builder.append(_gpioPin_2, "    ");
          _builder.append(", :input)");
          _builder.newLineIfNotEmpty();
          _builder.append("  ");
          _builder.append("  ");
          _builder.append("value = Circuits.GPIO.read(gpio)");
          _builder.newLine();
          _builder.append("  ");
          _builder.append("  ");
          _builder.append("Circuits.GPIO.close(gpio)");
          _builder.newLine();
          _builder.append("  ");
          _builder.append("  ");
          _builder.append("Logger.debug(\"");
          String _name_6 = sensor.getName();
          _builder.append(_name_6, "    ");
          _builder.append(": #{value}\")");
          _builder.newLineIfNotEmpty();
          {
            EList<Trigger> _triggers_1 = sensor.getTriggers();
            for(final Trigger trigger_1 : _triggers_1) {
              _builder.append("  ");
              _builder.append("  ");
              _builder.append("# Trigger ");
              String _name_7 = trigger_1.getName();
              _builder.append(_name_7, "    ");
              _builder.append(": when value ");
              String _elixirOp_2 = this.toElixirOp(trigger_1.getCondition().getOperator());
              _builder.append(_elixirOp_2, "    ");
              _builder.append(" ");
              int _evalNumExpr_4 = this.evalNumExpr(trigger_1.getCondition().getRight());
              _builder.append(_evalNumExpr_4, "    ");
              _builder.newLineIfNotEmpty();
              _builder.append("  ");
              _builder.append("  ");
              _builder.append("if value ");
              String _elixirOp_3 = this.toElixirOp(trigger_1.getCondition().getOperator());
              _builder.append(_elixirOp_3, "    ");
              _builder.append(" ");
              int _evalNumExpr_5 = this.evalNumExpr(trigger_1.getCondition().getRight());
              _builder.append(_evalNumExpr_5, "    ");
              _builder.append(" do");
              _builder.newLineIfNotEmpty();
              {
                EList<TriggerAction> _actions_1 = trigger_1.getActions();
                for(final TriggerAction action_1 : _actions_1) {
                  _builder.append("  ");
                  _builder.append("  ");
                  _builder.append("  ");
                  _builder.append("Tortoise311.publish(");
                  _builder.newLine();
                  _builder.append("  ");
                  _builder.append("  ");
                  _builder.append("  ");
                  _builder.append("  ");
                  _builder.append("\"");
                  String _name_8 = node.getName();
                  _builder.append(_name_8, "        ");
                  _builder.append("_sensor_node\",");
                  _builder.newLineIfNotEmpty();
                  _builder.append("  ");
                  _builder.append("  ");
                  _builder.append("  ");
                  _builder.append("  ");
                  _builder.append("\"");
                  String _picStr_1 = this.topicStr(action_1.getTopic());
                  _builder.append(_picStr_1, "        ");
                  _builder.append("\",");
                  _builder.newLineIfNotEmpty();
                  _builder.append("  ");
                  _builder.append("  ");
                  _builder.append("  ");
                  _builder.append("  ");
                  _builder.append("\"#{value}\",");
                  _builder.newLine();
                  _builder.append("  ");
                  _builder.append("  ");
                  _builder.append("  ");
                  _builder.append("  ");
                  _builder.append("qos: 1");
                  _builder.newLine();
                  _builder.append("  ");
                  _builder.append("  ");
                  _builder.append("  ");
                  _builder.append(")");
                  _builder.newLine();
                }
              }
              _builder.append("  ");
              _builder.append("  ");
              _builder.append("end");
              _builder.newLine();
            }
          }
          _builder.append("  ");
          _builder.append("  ");
          _builder.append("{:noreply, %{last_value: value}}");
          _builder.newLine();
          _builder.append("  ");
          _builder.append("end");
          _builder.newLine();
          _builder.newLine();
        } else {
          String _string_2 = sensor.getType().toString();
          boolean _equals_2 = Objects.equals(_string_2, "MOTION_PIR");
          if (_equals_2) {
            _builder.append("  ");
            _builder.append("# HC-SR501 PIR Motion Sensor (interrupt-driven)");
            _builder.newLine();
            _builder.append("  ");
            _builder.append("# No polling — GPIO ");
            int _gpioPin_3 = sensor.getGpioPin();
            _builder.append(_gpioPin_3, "  ");
            _builder.append(" interrupt fires instantly on change");
            _builder.newLineIfNotEmpty();
            _builder.newLine();
            _builder.append("  ");
            _builder.append("def init(_) do");
            _builder.newLine();
            _builder.append("  ");
            _builder.append("  ");
            _builder.append("{:ok, gpio} = Circuits.GPIO.open(");
            int _gpioPin_4 = sensor.getGpioPin();
            _builder.append(_gpioPin_4, "    ");
            _builder.append(", :input)");
            _builder.newLineIfNotEmpty();
            _builder.append("  ");
            _builder.append("  ");
            _builder.append("Circuits.GPIO.set_interrupts(gpio, :both)");
            _builder.newLine();
            _builder.append("  ");
            _builder.append("  ");
            _builder.append("Logger.info(\"");
            String _name_9 = sensor.getName();
            _builder.append(_name_9, "    ");
            _builder.append(": listening on GPIO ");
            int _gpioPin_5 = sensor.getGpioPin();
            _builder.append(_gpioPin_5, "    ");
            _builder.append("\")");
            _builder.newLineIfNotEmpty();
            _builder.append("  ");
            _builder.append("  ");
            _builder.append("{:ok, gpio}");
            _builder.newLine();
            _builder.append("  ");
            _builder.append("end");
            _builder.newLine();
            _builder.newLine();
            {
              EList<Trigger> _triggers_2 = sensor.getTriggers();
              for(final Trigger trigger_2 : _triggers_2) {
                _builder.append("  ");
                _builder.append("# Trigger ");
                String _name_10 = trigger_2.getName();
                _builder.append(_name_10, "  ");
                _builder.append(": when pin_value ");
                String _elixirOp_4 = this.toElixirOp(trigger_2.getCondition().getOperator());
                _builder.append(_elixirOp_4, "  ");
                _builder.append(" ");
                int _evalNumExpr_6 = this.evalNumExpr(trigger_2.getCondition().getRight());
                _builder.append(_evalNumExpr_6, "  ");
                _builder.newLineIfNotEmpty();
                _builder.append("  ");
                _builder.append("def handle_info({:circuits_gpio, ");
                int _gpioPin_6 = sensor.getGpioPin();
                _builder.append(_gpioPin_6, "  ");
                _builder.append(", _timestamp, pin_value}, state)");
                _builder.newLineIfNotEmpty();
                _builder.append("  ");
                _builder.append("    ");
                _builder.append("when pin_value ");
                String _elixirOp_5 = this.toElixirOp(trigger_2.getCondition().getOperator());
                _builder.append(_elixirOp_5, "      ");
                _builder.append(" ");
                int _evalNumExpr_7 = this.evalNumExpr(trigger_2.getCondition().getRight());
                _builder.append(_evalNumExpr_7, "      ");
                _builder.append(" do");
                _builder.newLineIfNotEmpty();
                _builder.append("  ");
                _builder.append("  ");
                _builder.append("Logger.info(\"");
                String _name_11 = sensor.getName();
                _builder.append(_name_11, "    ");
                _builder.append(": ");
                String _name_12 = trigger_2.getName();
                _builder.append(_name_12, "    ");
                _builder.append(" fired (value=#{pin_value})\")");
                _builder.newLineIfNotEmpty();
                {
                  EList<TriggerAction> _actions_2 = trigger_2.getActions();
                  for(final TriggerAction action_2 : _actions_2) {
                    _builder.append("  ");
                    _builder.append("  ");
                    _builder.append("Tortoise311.publish(");
                    _builder.newLine();
                    _builder.append("  ");
                    _builder.append("  ");
                    _builder.append("  ");
                    _builder.append("\"");
                    String _name_13 = node.getName();
                    _builder.append(_name_13, "      ");
                    _builder.append("_sensor_node\",");
                    _builder.newLineIfNotEmpty();
                    _builder.append("  ");
                    _builder.append("  ");
                    _builder.append("  ");
                    _builder.append("\"");
                    String _picStr_2 = this.topicStr(action_2.getTopic());
                    _builder.append(_picStr_2, "      ");
                    _builder.append("\",");
                    _builder.newLineIfNotEmpty();
                    _builder.append("  ");
                    _builder.append("  ");
                    _builder.append("  ");
                    _builder.append("\"#{pin_value}\",");
                    _builder.newLine();
                    _builder.append("  ");
                    _builder.append("  ");
                    _builder.append("  ");
                    _builder.append("qos: 1");
                    _builder.newLine();
                    _builder.append("  ");
                    _builder.append("  ");
                    _builder.append(")");
                    _builder.newLine();
                  }
                }
                _builder.append("  ");
                _builder.append("  ");
                _builder.append("{:noreply, state}");
                _builder.newLine();
                _builder.append("  ");
                _builder.append("end");
                _builder.newLine();
                _builder.newLine();
              }
            }
          }
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
    _builder.append("  ");
    _builder.append("require Logger");
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
    _builder.append("Logger.info(\"");
    String _name = actuator.getName();
    _builder.append(_name, "    ");
    _builder.append(": ready on GPIO ");
    int _gpioPin_1 = actuator.getGpioPin();
    _builder.append(_gpioPin_1, "    ");
    _builder.append("\")");
    _builder.newLineIfNotEmpty();
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
    _builder.append("Logger.info(\"");
    String _name_1 = actuator.getName();
    _builder.append(_name_1, "    ");
    _builder.append(": ON\")");
    _builder.newLineIfNotEmpty();
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
    _builder.append("Logger.info(\"");
    String _name_2 = actuator.getName();
    _builder.append(_name_2, "    ");
    _builder.append(": OFF\")");
    _builder.newLineIfNotEmpty();
    _builder.append("    ");
    _builder.append("{:noreply, gpio}");
    _builder.newLine();
    _builder.append("  ");
    _builder.append("end");
    _builder.newLine();
    _builder.newLine();
    {
      EList<OnMessage> _messages = actuator.getMessages();
      for(final OnMessage msg : _messages) {
        {
          SampleRate _duration = msg.getDuration();
          boolean _tripleNotEquals = (_duration != null);
          if (_tripleNotEquals) {
            _builder.append("  ");
            _builder.append("# on message from ");
            String _name_3 = msg.getTopic().getName();
            _builder.append(_name_3, "  ");
            _builder.append(": turn ");
            State _state = msg.getState();
            _builder.append(_state, "  ");
            _builder.append(" for ");
            int _evalNumExpr = this.evalNumExpr(msg.getDuration().getValue());
            _builder.append(_evalNumExpr, "  ");
            _builder.append(" ");
            TimeUnit _unit = msg.getDuration().getUnit();
            _builder.append(_unit, "  ");
            _builder.newLineIfNotEmpty();
            _builder.append("  ");
            _builder.append("def handle_cast({:timed_on, \"");
            String _picStr = this.topicStr(msg.getTopic());
            _builder.append(_picStr, "  ");
            _builder.append("\"}, gpio) do");
            _builder.newLineIfNotEmpty();
            _builder.append("  ");
            _builder.append("  ");
            _builder.append("Circuits.GPIO.write(gpio, 1)");
            _builder.newLine();
            _builder.append("  ");
            _builder.append("  ");
            _builder.append("Logger.info(\"");
            String _name_4 = actuator.getName();
            _builder.append(_name_4, "    ");
            _builder.append(": ON for ");
            int _durationToMillis = this.durationToMillis(msg.getDuration());
            _builder.append(_durationToMillis, "    ");
            _builder.append("ms\")");
            _builder.newLineIfNotEmpty();
            _builder.append("  ");
            _builder.append("  ");
            _builder.append("Process.sleep(");
            int _durationToMillis_1 = this.durationToMillis(msg.getDuration());
            _builder.append(_durationToMillis_1, "    ");
            _builder.append(")");
            _builder.newLineIfNotEmpty();
            _builder.append("  ");
            _builder.append("  ");
            _builder.append("Circuits.GPIO.write(gpio, 0)");
            _builder.newLine();
            _builder.append("  ");
            _builder.append("  ");
            _builder.append("Logger.info(\"");
            String _name_5 = actuator.getName();
            _builder.append(_name_5, "    ");
            _builder.append(": OFF (timer expired)\")");
            _builder.newLineIfNotEmpty();
            _builder.append("  ");
            _builder.append("  ");
            _builder.append("{:noreply, gpio}");
            _builder.newLine();
            _builder.append("  ");
            _builder.append("end");
            _builder.newLine();
            _builder.newLine();
          }
        }
      }
    }
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
      EList<Topic> _subscribeTo = coord.getSubscribeTo();
      for(final Topic topic : _subscribeTo) {
        _builder.append("  ");
        final String[] parts = this.topicStr(topic).split("/");
        _builder.newLineIfNotEmpty();
        _builder.append("  ");
        _builder.append("# ");
        String _name = topic.getName();
        _builder.append(_name, "  ");
        _builder.append(" = \"");
        String _picStr = this.topicStr(topic);
        _builder.append(_picStr, "  ");
        _builder.append("\"");
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
        _builder.append("Logger.debug(\"MqttHandler: received ");
        String _picStr_1 = this.topicStr(topic);
        _builder.append(_picStr_1, "    ");
        _builder.append("\")");
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
    _builder.append("initial = %{");
    _builder.newLine();
    {
      Set<String> _uniqueStateKeys = this.uniqueStateKeys(coord);
      boolean _hasElements = false;
      for(final String key : _uniqueStateKeys) {
        if (!_hasElements) {
          _hasElements = true;
        } else {
          _builder.appendImmediate(",", "      ");
        }
        _builder.append("      ");
        _builder.append(key, "      ");
        _builder.append(": :unknown");
        _builder.newLineIfNotEmpty();
      }
    }
    _builder.append("    ");
    _builder.append("}");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("Logger.info(\"Coordinator started: #{inspect(initial)}\")");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("{:ok, initial}");
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
    _builder.append("Logger.debug(\"Coordinator state: #{inspect(new_state)}\")");
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
        _builder.append("# Rule: ");
        String _name = rule.getName();
        _builder.append(_name, "    ");
        _builder.newLineIfNotEmpty();
        _builder.append("    ");
        _builder.append("if ");
        String _generateBoolExpr = this.generateBoolExpr(rule.getCondition().getExpression());
        _builder.append(_generateBoolExpr, "    ");
        _builder.append(" do");
        _builder.newLineIfNotEmpty();
        _builder.append("    ");
        _builder.append("  ");
        _builder.append("Logger.error(\"ALERT [");
        String _name_1 = rule.getName();
        _builder.append(_name_1, "      ");
        _builder.append("]: ");
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
        String _name_2 = coord.getDeployedOn().getName();
        _builder.append(_name_2, "        ");
        _builder.append("_coordinator_node\",");
        _builder.newLineIfNotEmpty();
        _builder.append("    ");
        _builder.append("    ");
        _builder.append("\"");
        String _picStr = this.topicStr(rule.getActions().get(0).getTopic());
        _builder.append(_picStr, "        ");
        _builder.append("\",");
        _builder.newLineIfNotEmpty();
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
        _builder.append("    ");
        final String[] onParts = this.topicStr(actuator.getSubscribeTo().get(0)).split("/");
        _builder.newLineIfNotEmpty();
        _builder.append("    ");
        _builder.append("# ");
        String _name_3 = actuator.getName();
        _builder.append(_name_3, "    ");
        _builder.append(" (");
        ActuatorType _type = actuator.getType();
        _builder.append(_type, "    ");
        _builder.append(" on GPIO ");
        int _gpioPin = actuator.getGpioPin();
        _builder.append(_gpioPin, "    ");
        _builder.append(")");
        _builder.newLineIfNotEmpty();
        _builder.append("    ");
        _builder.append("if state.");
        String _get = onParts[0];
        _builder.append(_get, "    ");
        _builder.append(" == :");
        String _get_1 = onParts[1];
        _builder.append(_get_1, "    ");
        _builder.append(",");
        _builder.newLineIfNotEmpty();
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
      }
    }
    _builder.append("  ");
    _builder.append("end");
    _builder.newLine();
    _builder.append("end");
    _builder.newLine();
    return _builder;
  }

  public CharSequence generateTopologyJson(final elixir.of.things.elixirOfThings.System system) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("{");
    _builder.newLine();
    _builder.append("  ");
    _builder.append("\"systemName\": \"");
    String _name = system.getName();
    _builder.append(_name, "  ");
    _builder.append("\",");
    _builder.newLineIfNotEmpty();
    _builder.append("  ");
    _builder.append("\"broker\": {");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("\"host\": ");
    String _jsonString = this.jsonString(system.getBroker().getHost());
    _builder.append(_jsonString, "    ");
    _builder.append(",");
    _builder.newLineIfNotEmpty();
    _builder.append("    ");
    _builder.append("\"port\": ");
    int _port = system.getBroker().getPort();
    _builder.append(_port, "    ");
    _builder.newLineIfNotEmpty();
    _builder.append("  ");
    _builder.append("},");
    _builder.newLine();
    _builder.append("  ");
    _builder.append("\"nodes\": [");
    _builder.newLine();
    {
      EList<Node> _nodes = system.getNodes();
      boolean _hasElements = false;
      for(final Node node : _nodes) {
        if (!_hasElements) {
          _hasElements = true;
        } else {
          _builder.appendImmediate(",", "    ");
        }
        _builder.append("    ");
        _builder.append("{");
        _builder.newLine();
        _builder.append("    ");
        _builder.append("  ");
        _builder.append("\"id\": ");
        String _jsonString_1 = this.jsonString(node.getName());
        _builder.append(_jsonString_1, "      ");
        _builder.append(",");
        _builder.newLineIfNotEmpty();
        _builder.append("    ");
        _builder.append("  ");
        _builder.append("\"name\": ");
        String _jsonString_2 = this.jsonString(node.getName());
        _builder.append(_jsonString_2, "      ");
        _builder.newLineIfNotEmpty();
        _builder.append("    ");
        _builder.append("}");
        _builder.newLine();
      }
    }
    _builder.append("  ");
    _builder.append("],");
    _builder.newLine();
    _builder.append("  ");
    _builder.append("\"sensors\": [");
    _builder.newLine();
    {
      EList<Sensor> _sensors = system.getSensors();
      boolean _hasElements_1 = false;
      for(final Sensor sensor : _sensors) {
        if (!_hasElements_1) {
          _hasElements_1 = true;
        } else {
          _builder.appendImmediate(",", "    ");
        }
        _builder.append("    ");
        _builder.append("{");
        _builder.newLine();
        _builder.append("    ");
        _builder.append("  ");
        _builder.append("\"id\": ");
        String _jsonString_3 = this.jsonString(sensor.getName());
        _builder.append(_jsonString_3, "      ");
        _builder.append(",");
        _builder.newLineIfNotEmpty();
        _builder.append("    ");
        _builder.append("  ");
        _builder.append("\"name\": ");
        String _jsonString_4 = this.jsonString(sensor.getName());
        _builder.append(_jsonString_4, "      ");
        _builder.append(",");
        _builder.newLineIfNotEmpty();
        _builder.append("    ");
        _builder.append("  ");
        _builder.append("\"type\": ");
        String _jsonString_5 = this.jsonString(sensor.getType().toString());
        _builder.append(_jsonString_5, "      ");
        _builder.append(",");
        _builder.newLineIfNotEmpty();
        _builder.append("    ");
        _builder.append("  ");
        _builder.append("\"gpioPin\": ");
        int _gpioPin = sensor.getGpioPin();
        _builder.append(_gpioPin, "      ");
        _builder.append(",");
        _builder.newLineIfNotEmpty();
        _builder.append("    ");
        _builder.append("  ");
        _builder.append("\"sampleRateMs\": ");
        int _sampleRateToMillis = this.sampleRateToMillis(sensor.getSampleRate());
        _builder.append(_sampleRateToMillis, "      ");
        _builder.append(",");
        _builder.newLineIfNotEmpty();
        _builder.append("    ");
        _builder.append("  ");
        _builder.append("\"deployedOnNodeId\": ");
        String _jsonString_6 = this.jsonString(sensor.getDeployedOn().getName());
        _builder.append(_jsonString_6, "      ");
        _builder.newLineIfNotEmpty();
        _builder.append("    ");
        _builder.append("}");
        _builder.newLine();
      }
    }
    _builder.append("  ");
    _builder.append("],");
    _builder.newLine();
    _builder.append("  ");
    _builder.append("\"actuators\": [");
    _builder.newLine();
    {
      EList<Actuator> _actuators = system.getActuators();
      boolean _hasElements_2 = false;
      for(final Actuator actuator : _actuators) {
        if (!_hasElements_2) {
          _hasElements_2 = true;
        } else {
          _builder.appendImmediate(",", "    ");
        }
        _builder.append("    ");
        _builder.append("{");
        _builder.newLine();
        _builder.append("    ");
        _builder.append("  ");
        _builder.append("\"id\": ");
        String _jsonString_7 = this.jsonString(actuator.getName());
        _builder.append(_jsonString_7, "      ");
        _builder.append(",");
        _builder.newLineIfNotEmpty();
        _builder.append("    ");
        _builder.append("  ");
        _builder.append("\"name\": ");
        String _jsonString_8 = this.jsonString(actuator.getName());
        _builder.append(_jsonString_8, "      ");
        _builder.append(",");
        _builder.newLineIfNotEmpty();
        _builder.append("    ");
        _builder.append("  ");
        _builder.append("\"type\": ");
        String _jsonString_9 = this.jsonString(actuator.getType().toString());
        _builder.append(_jsonString_9, "      ");
        _builder.append(",");
        _builder.newLineIfNotEmpty();
        _builder.append("    ");
        _builder.append("  ");
        _builder.append("\"gpioPin\": ");
        int _gpioPin_1 = actuator.getGpioPin();
        _builder.append(_gpioPin_1, "      ");
        _builder.append(",");
        _builder.newLineIfNotEmpty();
        _builder.append("    ");
        _builder.append("  ");
        _builder.append("\"deployedOnNodeId\": ");
        String _jsonString_10 = this.jsonString(actuator.getDeployedOn().getName());
        _builder.append(_jsonString_10, "      ");
        _builder.newLineIfNotEmpty();
        _builder.append("    ");
        _builder.append("}");
        _builder.newLine();
      }
    }
    _builder.append("  ");
    _builder.append("],");
    _builder.newLine();
    _builder.append("  ");
    _builder.append("\"coordinators\": [");
    _builder.newLine();
    {
      EList<Coordinator> _coordinators = system.getCoordinators();
      boolean _hasElements_3 = false;
      for(final Coordinator coord : _coordinators) {
        if (!_hasElements_3) {
          _hasElements_3 = true;
        } else {
          _builder.appendImmediate(",", "    ");
        }
        _builder.append("    ");
        _builder.append("{");
        _builder.newLine();
        _builder.append("    ");
        _builder.append("  ");
        _builder.append("\"id\": ");
        String _jsonString_11 = this.jsonString(coord.getName());
        _builder.append(_jsonString_11, "      ");
        _builder.append(",");
        _builder.newLineIfNotEmpty();
        _builder.append("    ");
        _builder.append("  ");
        _builder.append("\"name\": ");
        String _jsonString_12 = this.jsonString(coord.getName());
        _builder.append(_jsonString_12, "      ");
        _builder.append(",");
        _builder.newLineIfNotEmpty();
        _builder.append("    ");
        _builder.append("  ");
        _builder.append("\"deployedOnNodeId\": ");
        String _jsonString_13 = this.jsonString(coord.getDeployedOn().getName());
        _builder.append(_jsonString_13, "      ");
        _builder.newLineIfNotEmpty();
        _builder.append("    ");
        _builder.append("}");
        _builder.newLine();
      }
    }
    _builder.append("  ");
    _builder.append("]");
    _builder.newLine();
    _builder.append("}");
    _builder.newLine();
    return _builder;
  }

  public CharSequence generateTopicsJson(final elixir.of.things.elixirOfThings.System system) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("{");
    _builder.newLine();
    _builder.append("  ");
    _builder.append("\"systemName\": \"");
    String _name = system.getName();
    _builder.append(_name, "  ");
    _builder.append("\",");
    _builder.newLineIfNotEmpty();
    _builder.append("  ");
    _builder.append("\"broker\": {");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("\"host\": ");
    String _jsonString = this.jsonString(system.getBroker().getHost());
    _builder.append(_jsonString, "    ");
    _builder.append(",");
    _builder.newLineIfNotEmpty();
    _builder.append("    ");
    _builder.append("\"port\": ");
    int _port = system.getBroker().getPort();
    _builder.append(_port, "    ");
    _builder.newLineIfNotEmpty();
    _builder.append("  ");
    _builder.append("},");
    _builder.newLine();
    _builder.append("  ");
    _builder.append("\"topics\": [");
    _builder.newLine();
    {
      EList<Topic> _topics = system.getTopics();
      boolean _hasElements = false;
      for(final Topic topic : _topics) {
        if (!_hasElements) {
          _hasElements = true;
        } else {
          _builder.appendImmediate(",", "    ");
        }
        _builder.append("    ");
        _builder.append("{");
        _builder.newLine();
        _builder.append("    ");
        _builder.append("  ");
        _builder.append("\"id\": ");
        String _jsonString_1 = this.jsonString(topic.getName());
        _builder.append(_jsonString_1, "      ");
        _builder.append(",");
        _builder.newLineIfNotEmpty();
        _builder.append("    ");
        _builder.append("  ");
        _builder.append("\"name\": ");
        String _jsonString_2 = this.jsonString(topic.getName());
        _builder.append(_jsonString_2, "      ");
        _builder.append(",");
        _builder.newLineIfNotEmpty();
        _builder.append("    ");
        _builder.append("  ");
        _builder.append("\"path\": ");
        String _jsonString_3 = this.jsonString(topic.getTopicString());
        _builder.append(_jsonString_3, "      ");
        _builder.append(",");
        _builder.newLineIfNotEmpty();
        _builder.append("    ");
        _builder.append("  ");
        _builder.append("\"qos\": ");
        int _ordinal = topic.getQos().ordinal();
        _builder.append(_ordinal, "      ");
        _builder.append(",");
        _builder.newLineIfNotEmpty();
        _builder.append("    ");
        _builder.append("  ");
        _builder.append("\"producers\": [");
        _builder.newLine();
        _builder.append("    ");
        _builder.append("    ");
        final List<String> producerIds = this.findProducers(system, topic);
        _builder.newLineIfNotEmpty();
        {
          boolean _hasElements_1 = false;
          for(final String p : producerIds) {
            if (!_hasElements_1) {
              _hasElements_1 = true;
            } else {
              _builder.appendImmediate(",", "        ");
            }
            _builder.append("    ");
            _builder.append("    ");
            String _jsonString_4 = this.jsonString(p);
            _builder.append(_jsonString_4, "        ");
            _builder.newLineIfNotEmpty();
          }
        }
        _builder.append("    ");
        _builder.append("  ");
        _builder.append("],");
        _builder.newLine();
        _builder.append("    ");
        _builder.append("  ");
        _builder.append("\"consumers\": [");
        _builder.newLine();
        _builder.append("    ");
        _builder.append("    ");
        final List<String> consumerIds = this.findConsumers(system, topic);
        _builder.newLineIfNotEmpty();
        {
          boolean _hasElements_2 = false;
          for(final String c : consumerIds) {
            if (!_hasElements_2) {
              _hasElements_2 = true;
            } else {
              _builder.appendImmediate(",", "        ");
            }
            _builder.append("    ");
            _builder.append("    ");
            String _jsonString_5 = this.jsonString(c);
            _builder.append(_jsonString_5, "        ");
            _builder.newLineIfNotEmpty();
          }
        }
        _builder.append("    ");
        _builder.append("  ");
        _builder.append("]");
        _builder.newLine();
        _builder.append("    ");
        _builder.append("}");
        _builder.newLine();
      }
    }
    _builder.append("  ");
    _builder.append("]");
    _builder.newLine();
    _builder.append("}");
    _builder.newLine();
    return _builder;
  }

  public String jsonString(final String value) {
    String _replace = value.replace("\\", "\\\\").replace("\"", "\\\"");
    String _plus = ("\"" + _replace);
    return (_plus + "\"");
  }

  public List<String> findProducers(final elixir.of.things.elixirOfThings.System system, final Topic topic) {
    List<String> _xblockexpression = null;
    {
      final ArrayList<String> ids = CollectionLiterals.<String>newArrayList();
      EList<Sensor> _sensors = system.getSensors();
      for (final Sensor sensor : _sensors) {
        EList<Trigger> _triggers = sensor.getTriggers();
        for (final Trigger trigger : _triggers) {
          final Function1<TriggerAction, Boolean> _function = (TriggerAction action) -> {
            Topic _topic = action.getTopic();
            return Boolean.valueOf(Objects.equals(_topic, topic));
          };
          boolean _exists = IterableExtensions.<TriggerAction>exists(trigger.getActions(), _function);
          if (_exists) {
            ids.add(sensor.getName());
          }
        }
      }
      EList<Coordinator> _coordinators = system.getCoordinators();
      for (final Coordinator coord : _coordinators) {
        final Function1<Rule, Boolean> _function_1 = (Rule rule) -> {
          final Function1<RuleAction, Boolean> _function_2 = (RuleAction action) -> {
            Topic _topic = action.getTopic();
            return Boolean.valueOf(Objects.equals(_topic, topic));
          };
          return Boolean.valueOf(IterableExtensions.<RuleAction>exists(rule.getActions(), _function_2));
        };
        boolean _exists_1 = IterableExtensions.<Rule>exists(coord.getRules(), _function_1);
        if (_exists_1) {
          ids.add(coord.getName());
        }
      }
      _xblockexpression = IterableExtensions.<String>toList(IterableExtensions.<String>toSet(ids));
    }
    return _xblockexpression;
  }

  public List<String> findConsumers(final elixir.of.things.elixirOfThings.System system, final Topic topic) {
    List<String> _xblockexpression = null;
    {
      final ArrayList<String> ids = CollectionLiterals.<String>newArrayList();
      EList<Coordinator> _coordinators = system.getCoordinators();
      for (final Coordinator coord : _coordinators) {
        boolean _contains = coord.getSubscribeTo().contains(topic);
        if (_contains) {
          ids.add(coord.getName());
        }
      }
      EList<Actuator> _actuators = system.getActuators();
      for (final Actuator actuator : _actuators) {
        boolean _contains_1 = actuator.getSubscribeTo().contains(topic);
        if (_contains_1) {
          ids.add(actuator.getName());
        }
      }
      _xblockexpression = IterableExtensions.<String>toList(IterableExtensions.<String>toSet(ids));
    }
    return _xblockexpression;
  }
}

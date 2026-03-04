## Xtext EBNF grammar

```
grammar elixir.of.things.ElixirOfThings with org.eclipse.xtext.common.Terminals
generate elixirOfThings "http://www.of.elixir/things/ElixirOfThings"

// Root rule
System:
	'system' name=ID '{'
		broker=Broker
		nodes+=Node+
		topics+=Topic*
		(sensors+=Sensor | actuators+=Actuator | coordinators+=Coordinator)*
	'}';

// Structural elements
Broker:
	'broker:' host=STRING 'port:' port=INT;

Node:
	'node' name=ID '{' 'ip:' ipAddress=STRING '}';

Topic:
	'topic' name=STRING 'qos:' qos=QoS;

Sensor:
	'sensor' name=ID '{'
		'type:' type=SensorType
		'gpioPin:' gpioPin=INT
		'sampleRate:' sampleRate=Duration
		'deployedOn:' deployedOn=[Node]
		triggers+=Trigger*
	'}';

Trigger:
	'trigger' name=ID '{'
		condition=TriggerCondition ':' actions+=TriggerAction+
	'}';

TriggerCondition:
	'when' left=Expression operator=Operator right=Expression;

TriggerAction:
	'publish' topic=STRING ':' '{' fields+=Expression (',' fields+=Expression)* '}';

Actuator:
	'actuator' name=ID '{'
		'type:' type=ActuatorType
		'gpioPin:' gpioPin=INT
		'deployedOn:' deployedOn=[Node]
		'subscribeTo:' subscribeTo+=STRING (',' subscribeTo+=STRING)*
		messages+=OnMessage*
	'}';

OnMessage:
	'on' 'message' 'from' topic=STRING ':'
		'turn' state=State ('for' duration=Duration)?;

Coordinator:
	'coordinator' name=ID '{'
		'deployedOn:' deployedOn=[Node]
		'subscribeTo:' subscribeTo+=STRING (',' subscribeTo+=STRING)*
		rules+=Rule*
	'}';

Rule:
	'rule' name=ID '{'
		condition=RuleCondition ':' actions+=RuleAction+
	'}';

RuleCondition:
	'when' topics+=TopicPath (operators+=LogicalOp topics+=TopicPath)*;

TopicPath:
	segments+=ID ('/' segments+=ID)*;

RuleAction:
	'publish' topic=STRING ':' '{' ('message:' message=STRING)? '}';

// Common
Duration:
	value=INT unit=TimeUnit;

Expression:
	{ValueExpr} 'value' |
	{TimestampExpr} 'timestamp' |
	{LiteralInt} value=INT |
	{LiteralString} value=STRING;
  

// Enumerations
enum SensorType:
	TEMPERATURE | HUMIDITY | MOTION | LIGHT | DISTANCE;


enum ActuatorType: 
	RELAY | LED | BUZZER | SERVO;

enum QoS:
	AT_MOST_ONCE | AT_LEAST_ONCE | EXACTLY_ONCE;

enum Operator:
	GREATER_THAN='>' | LESS_THAN='<' | GREATER_EQ='>=' | LESS_EQ='<=' | EQUALS='==';

enum LogicalOp:
	AND='and' | AND_SYM='&' | OR='or' | OR_SYM='|' | NOT='not' | NOT_SYM='!';

enum State:
	ON | OFF;

enum TimeUnit:
	SEC='sec' | MIN='min' | MS='ms';
```


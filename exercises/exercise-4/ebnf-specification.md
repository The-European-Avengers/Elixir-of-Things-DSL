# EBNF Specification
## Program example of our DSL

```
system SafetyAlert {
	broker: "192.168.1.100" port: 1883 

	node pi4 { ip: "192.168.1.10" }
	node pi5 { ip: "192.168.1.11" }
 
	topic "temperature/high" qos: AT_LEAST_ONCE
	topic "distance/close" qos: AT_MOST_ONCE
	topic "alerts/warning" qos: AT_LEAST_ONCE

	sensor temp_sensor {
		type: TEMPERATURE
		gpioPin: 4
		sampleRate: 5 sec
		deployedOn: pi4
  
		trigger high_temp {
			when value > 30:
				publish "temperature/high": {value, timestamp}
		}
	}
 

	sensor distance_sensor {
		type: DISTANCE
		gpioPin: 27
		sampleRate: 2 sec
		deployedOn: pi4

		trigger too_close {
			when value < 20:
				publish "distance/close": {value, timestamp}
		}
	}


	coordinator room_safety {
		deployedOn: pi5
		subscribeTo: "temperature/high", "distance/close"

		rule heat_and_close {
			when temperature/high and distance/close:
				publish "alerts/warning": {message: "High temp + object nearby"}
		}
	}

  
	actuator buzzer {
		type: BUZZER
		gpioPin: 23
		deployedOn: pi5
		subscribeTo: "alerts/warning"
		
		on message from "alerts/warning":
			turn ON for 3 sec
	}
}
```

## Program Syntax as an EBNF

```
Terminals:
letter      := [a-zA-Z_]
digit       := [0-9]
ID          := letter (letter | digit)*
INTEGER     := digit+
STRING      := '"' ([^"\\] | '\\"')* '"'
TOPIC_PATH  := letter+ ('/' letter+)*

Enumerations:
SensorType   := 'TEMPERATURE' | 'HUMIDITY' | 'MOTION' | 'LIGHT' | 'DISTANCE'
ActuatorType := 'RELAY' | 'LED' | 'BUZZER' | 'SERVO'
QoS          := 'AT_MOST_ONCE' | 'AT_LEAST_ONCE' | 'EXACTLY_ONCE'
Operator     := '>' | '<' | '>=' | '<=' | '=='
LogicalOp    := 'and' | '&' | 'or' |  '|' | 'not' | '!'
State        := 'ON' | 'OFF'
TimeUnit     := 'sec' | 'min' | 'ms'

Commons:
Duration     := INTEGER TimeUnit
Expression   := 'value' | 'timestamp' | INTEGER | STRING
TopicList    := STRING (',' STRING)*

(* System - root rule *)
System       := 'system' ID '{' Broker Node+ Topic* Sensor* Actuator* Coordinator* '}'

Structural elements:
Broker       := 'broker:' STRING 'port:' INTEGER
Node         := 'node' ID '{' 'ip:' STRING '}'
Topic        := 'topic' STRING 'qos:' QoS

Sensor       := 'sensor' ID '{'
                    'type:' SensorType
                    'gpioPin:' INTEGER
                    'sampleRate:' Duration
                    'deployedOn:' ID
                    Trigger*
                '}'

Trigger      := 'trigger' ID '{' TriggerCondition ':' TriggerAction+ '}'
TriggerCondition := 'when' Expression Operator Expression
TriggerAction    := 'publish' STRING ':' '{' Expression (',' Expression)* '}'

Actuator     := 'actuator' ID '{'
                    'type:' ActuatorType
                    'gpioPin:' INTEGER
                    'deployedOn:' ID
                    'subscribeTo:' TopicList
                    OnMessage*
                '}'

OnMessage    := 'on' 'message' 'from' STRING ':' 'turn' State ('for' Duration)?

Coordinator  := 'coordinator' ID '{'
                    'deployedOn:' ID
                    'subscribeTo:' TopicList
                    Rule*
                '}'

Rule          := 'rule' ID '{' RuleCondition ':' RuleAction+ '}'
RuleCondition := 'when' TOPIC_PATH (LogicalOp TOPIC_PATH)*
RuleAction    := 'publish' STRING ':' '{' ('message:' STRING)? '}'
```


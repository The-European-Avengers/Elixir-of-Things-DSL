# IoT Event Processing DSL - Metamodel Design

## Domain

Our DSL targets **IoT event processing** for distributed Raspberry Pi systems. It allows domain experts to define sensor-actuator behaviors declaratively, generating Elixir/Nerves code that communicates via **MQTT**.

## Representative Examples

### Example 1: Temperature-Controlled Cooling

A temperature sensor on Raspberry Pi 4 reads from a GPIO pin every 5 seconds. When the temperature exceeds 30°C, it publishes a message to an MQTT topic. A cooling fan (relay actuator) on Raspberry Pi 5 subscribes to that topic and turns on. When the temperature drops below 20°C, the sensor publishes to a different topic, and the fan turns off.

### Example 2: Motion-Activated Warning LED

A PIR motion sensor on Pi 4 checks for motion every second. When motion is detected, it publishes to a topic. An LED on Pi 5 subscribes and lights up. When motion clears, the sensor publishes to a second topic, and the LED turns off.

### Example 3: Multi-Sensor Safety Alert

Neither the temperature sensor nor the distance sensor alone can determine a fire risk. A coordinator on Pi 5 subscribes to both the high-temperature topic and the proximity topic. When it receives messages from both, meaning it is hot AND an object is nearby, it publishes to an alert topic. A buzzer subscribes to that alert topic and sounds for 3 seconds.

## Common Properties

From these examples we identify main concepts:

- **Nodes**: Physical Raspberry Pi devices with network addresses
- **Sensors**: Read GPIO pins at a defined sample rate, deployed on a node, publish data to topics
- **Actuators**: Control GPIO pins, deployed on a node, subscribe to topics, react with a physical action (ON/OFF, optionally timed)
- **Topics**: MQTT communication channels with a Quality of Service level — decouple sensors from actuators
- **Coordinators**: Subscribe to multiple topics, apply cross-sensor rules, and publish derived events to new topics
- **Triggers** (on sensors): Evaluate conditions against the sensor's own reading and fire actions (e.g., publish to topic)
- **Rules** (on coordinators): Evaluate conditions against messages from multiple topics and fire actions
- **Conditions**: Compare values using operators (>, <, ==); can be combined with logical operators (AND, OR) to form compound conditions; optionally with a temporal duration requirement
- **Actions**: Either publish a message to a topic or activate an actuator
- **Expressions**: Values used in conditions — either a fixed literal (e.g., 30, true) or a dynamic sensor reading (e.g., value, timestamp)
- **Actuator Actions**: Map an incoming topic to a physical response — define which state (ON/OFF) and optionally for how long

---

## Metamodel (Class Diagram)

The class diagram below captures these common properties. 

![Meta-model Diagram](doc/img/meta-model.png)

---

## Syntax

Based on our metamodel, we designed a declarative syntax that maps directly to the metamodel's classes. The syntax prioritizes readability for domain experts – someone familiar with IoT concepts should be able to read and write these programs without knowledge of Elixir, MQTT internals, or distributed systems.
Below are three DSL programs corresponding to the metamodel's representative examples:

### Example 1: Temperature-Controlled Cooling
```
system TemperatureCooling {
    broker: "192.168.1.100" port: 1883

    node pi4 { ip: "192.168.1.10" }
    node pi5 { ip: "192.168.1.11" }

    topic "temperature/high"    qos: AT_LEAST_ONCE
    topic "temperature/normal"  qos: AT_LEAST_ONCE

    sensor temp_sensor {
        type: TEMPERATURE
        gpioPin: 4
        sampleRate: 5 seconds
        deployedOn: pi4

        trigger high_temp {
            when value > 30:
                publish "temperature/high": {value, timestamp}
        }

        trigger normal_temp {
            when value < 20:
                publish "temperature/normal": {value, timestamp}
        }
    }

    actuator cooling_fan {
        type: RELAY
        gpioPin: 24
        deployedOn: pi5
        subscribeTo: "temperature/high", "temperature/normal"

        on message from "temperature/high":
            turn ON

        on message from "temperature/normal":
            turn OFF
    }
}
```
### Example 2: Motion-Activated Warning LED

```
system MotionWarning {
    broker: "192.168.1.100" port: 1883

    node pi4 { ip: "192.168.1.10" }
    node pi5 { ip: "192.168.1.11" }

    topic "motion/detected" qos: AT_LEAST_ONCE
    topic "motion/clear"    qos: AT_LEAST_ONCE

    sensor motion_sensor {
        type: MOTION
        gpioPin: 17
        sampleRate: 1 second
        deployedOn: pi4

        trigger motion_on {
            when value == true:
                publish "motion/detected": {value, timestamp}
        }

        trigger motion_off {
            when value == false:
                publish "motion/clear": {value, timestamp}
        }
    }

    actuator warning_led {
        type: LED
        gpioPin: 18
        deployedOn: pi5
        subscribeTo: "motion/detected", "motion/clear"

        on message from "motion/detected":
            turn ON

        on message from "motion/clear":
            turn OFF
    }
}
```
### Example 3: Multi-Sensor Safety Alert

```
system SafetyAlert {
    broker: "192.168.1.100" port: 1883

    node pi4 { ip: "192.168.1.10" }
    node pi5 { ip: "192.168.1.11" }

    topic "temperature/high" qos: AT_LEAST_ONCE
    topic "distance/close"   qos: AT_MOST_ONCE
    topic "alerts/warning"   qos: AT_LEAST_ONCE

    sensor temp_sensor {
        type: TEMPERATURE
        gpioPin: 4
        sampleRate: 5 seconds
        deployedOn: pi4

        trigger high_temp {
            when value > 30:
                publish "temperature/high": {value, timestamp}
        }
    }

    sensor distance_sensor {
        type: DISTANCE
        gpioPin: 27
        sampleRate: 2 seconds
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
            when temperature/high AND distance/close:
                publish "alerts/warning": {message: "High temp + object nearby"}
        }
    }

    actuator buzzer {
        type: BUZZER
        gpioPin: 23
        deployedOn: pi5
        subscribeTo: "alerts/warning"

        on message from "alerts/warning":
            turn ON for 3 seconds
    }
}
```


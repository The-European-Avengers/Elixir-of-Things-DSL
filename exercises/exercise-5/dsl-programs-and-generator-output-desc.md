## DSL Programs and Generator Output Description

### Program 1: Multi-Sensor Safety Alert (Real Hardware)

This program runs on two Raspberry Pis connected over WiFi via MQTT. Pi5 reads sensors and publishes events. Pi4 subscribes, evaluates rules, and drives physical actuators.

```
system SafetyAlert {
    broker: "192.168.1.198" port: 1883

    node pi5 { ip: "192.168.1.199" }
    node pi4 { ip: "192.168.1.10" }

    topic "temperature/high" qos: AT_LEAST_ONCE
    topic "temperature/low"  qos: AT_LEAST_ONCE
    topic "motion/detected"  qos: AT_LEAST_ONCE
    topic "motion/still"     qos: AT_LEAST_ONCE
    topic "alerts/warning"   qos: AT_LEAST_ONCE

    sensor temp_sensor {
        type: TEMPERATURE
        gpioPin: 4
        sampleRate: 5 sec
        deployedOn: pi5
        trigger high_temp {
            when value > 25:
                publish "temperature/high": {value}
        }
        trigger low_temp {
            when value <= 25:
                publish "temperature/low": {value}
        }
    }

    sensor motion_sensor {
        type: MOTION
        gpioPin: 17
        sampleRate: 0 sec
        deployedOn: pi5
        trigger motion_start {
            when value == 1:
                publish "motion/detected": {value}
        }
        trigger motion_stop {
            when value == 0:
                publish "motion/still": {value}
        }
    }

    coordinator room_safety {
        deployedOn: pi4
        subscribeTo: "temperature/high", "temperature/low",
                     "motion/detected", "motion/still"
        rule heat_and_motion {
            when temperature/high and motion/detected:
                publish "alerts/warning": {message: "High temp + motion nearby"}
        }
    }

    actuator warning_led {
        type: LED
        gpioPin: 20
        deployedOn: pi4
        subscribeTo: "temperature/high", "temperature/low"
        on message from "temperature/high": turn ON
        on message from "temperature/low":  turn OFF
    }

    actuator alarm_buzzer {
        type: BUZZER
        gpioPin: 18
        deployedOn: pi4
        subscribeTo: "motion/detected", "motion/still"
        on message from "motion/detected": turn ON
        on message from "motion/still":    turn OFF
    }
}
```

---

### Program 2: Smart Greenhouse Monitor

A single-coordinator system where soil moisture and temperature sensors trigger a water pump and a status LED on the same node.

```
system Greenhouse {
    broker: "192.168.1.198" port: 1883

    node pi_sensor     { ip: "192.168.1.20" }
    node pi_controller { ip: "192.168.1.21" }

    topic "soil/dry"          qos: AT_LEAST_ONCE
    topic "soil/wet"          qos: AT_LEAST_ONCE
    topic "temp/hot"          qos: AT_LEAST_ONCE
    topic "temp/normal"       qos: AT_LEAST_ONCE
    topic "alerts/greenhouse" qos: AT_LEAST_ONCE

    sensor soil_sensor {
        type: HUMIDITY
        gpioPin: 21
        sampleRate: 10 sec
        deployedOn: pi_sensor
        trigger needs_water {
            when value < 30:
                publish "soil/dry": {value}
        }
        trigger has_water {
            when value >= 30:
                publish "soil/wet": {value}
        }
    }

    sensor temp_sensor {
        type: TEMPERATURE
        gpioPin: 4
        sampleRate: 5 sec
        deployedOn: pi_sensor
        trigger too_hot {
            when value > 35:
                publish "temp/hot": {value}
        }
        trigger normal_temp {
            when value <= 35:
                publish "temp/normal": {value}
        }
    }

    coordinator greenhouse_brain {
        deployedOn: pi_controller
        subscribeTo: "soil/dry", "soil/wet", "temp/hot", "temp/normal"
        rule dry_and_hot {
            when soil/dry and temp/hot:
                publish "alerts/greenhouse": {message: "Dry soil + high temp"}
        }
    }

    actuator water_pump {
        type: RELAY
        gpioPin: 26
        deployedOn: pi_controller
        subscribeTo: "soil/dry", "soil/wet"
        on message from "soil/dry": turn ON for 10 sec
        on message from "soil/wet": turn OFF
    }

    actuator status_led {
        type: LED
        gpioPin: 18
        deployedOn: pi_controller
        subscribeTo: "temp/hot", "temp/normal"
        on message from "temp/hot":    turn ON
        on message from "temp/normal": turn OFF
    }
}
```

---

### Program 3: Door Security System

A single-node system where one Pi handles both sensing and actuation. A motion sensor on the door triggers a buzzer and LED on the same device.

```
system DoorSecurity {
    broker: "192.168.1.198" port: 1883

    node security_pi { ip: "192.168.1.30" }

    topic "door/opened"      qos: AT_LEAST_ONCE
    topic "door/closed"      qos: AT_LEAST_ONCE
    topic "alerts/intruder"  qos: AT_LEAST_ONCE

    sensor door_sensor {
        type: MOTION
        gpioPin: 17
        sampleRate: 0 sec
        deployedOn: security_pi
        trigger door_open {
            when value == 1:
                publish "door/opened": {value}
        }
        trigger door_close {
            when value == 0:
                publish "door/closed": {value}
        }
    }

    coordinator door_monitor {
        deployedOn: security_pi
        subscribeTo: "door/opened", "door/closed"
        rule intruder_alert {
            when door/opened:
                publish "alerts/intruder": {message: "Door opened - check entry"}
        }
    }

    actuator entry_buzzer {
        type: BUZZER
        gpioPin: 23
        deployedOn: security_pi
        subscribeTo: "door/opened", "door/closed"
        on message from "door/opened": turn ON for 2 sec
        on message from "door/closed": turn OFF
    }

    actuator entry_led {
        type: LED
        gpioPin: 18
        deployedOn: security_pi
        subscribeTo: "door/opened", "door/closed"
        on message from "door/opened": turn ON
        on message from "door/closed": turn OFF
    }
}
```

---

### Generator Output Description

The code generator takes the DSL model and produces a complete set of Elixir/Nerves source files, grouped by deployment node. For every `node` defined in the system, the generator creates a separate folder under `src-gen/lib/<node_name>/` containing:

**`application.ex`** — The OTP Application supervisor. It wires up the MQTT broker connection via Tortoise311, injects the correct handler (a custom `MqttHandler` for coordinator nodes, the default logger for sensor-only nodes), lists all MQTT topic subscriptions, and starts all child GenServer processes in the supervision tree.

**`<sensor_name>.ex`** — One GenServer per sensor. For `TEMPERATURE` sensors the generator produces a polling loop using `:timer.send_interval` that reads the DS18B20 1-Wire file at the configured `sampleRate` and publishes to the corresponding MQTT topic only when the state changes. For `MOTION` sensors the generator produces an interrupt-driven GenServer using `Circuits.GPIO.set_interrupts` that reacts instantly to HC-SR501 pin changes.

**`<actuator_name>.ex`** — One GenServer per actuator with a `turn_on/0` and `turn_off/0` public API. It opens the GPIO pin as an output on startup and writes `1` or `0` in response to cast messages from the Coordinator.

**`mqtt_handler.ex`** — A `Tortoise311.Handler` implementation that receives incoming MQTT messages and routes each topic to the Coordinator via `update_state/2`. Generated only for nodes that host a coordinator.

**`coordinator.ex`** — A stateful GenServer acting as a rule engine. It maintains an in-memory map of the last known state of every sensor topic (e.g. `%{temperature: :low, motion: :still}`). On every update it evaluates all DSL rules using pattern matching and directly calls `turn_on/turn_off` on the appropriate actuator modules. If a rule condition is met it also publishes an alert to the MQTT broker.

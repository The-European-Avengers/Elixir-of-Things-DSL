# DSL Programs and Generator Output Description

---

## Program 1: Multi-Sensor Safety Alert (Real Hardware)

This program runs on two Raspberry Pis connected over WiFi via MQTT. Pi5 reads sensors and publishes events. Pi4 subscribes, evaluates rules, and drives physical actuators.

The program demonstrates:

- Named topic variables referenced by ID across sensors, actuators and coordinator
- Arithmetic expressions in `sampleRate` and trigger thresholds (`2 * 5 sec`, `20 + 5`)
- Left-recursive boolean rule conditions with operator precedence (`and`, `or`, `not`)
- Parenthesised expressions overriding default precedence
- Timed actuator activation (`turn ON for 2 * 5 sec`)

```
system SafetyAlert {

    broker: "192.168.1.198" port: 1883

    node pi5
    node pi4

    // Named topic variables — referenced by ID everywhere, no raw strings
    topic temp_high  = ("temperature/high", AT_LEAST_ONCE)
    topic temp_low   = ("temperature/low",  AT_LEAST_ONCE)
    topic motion_on  = ("motion/detected",  AT_LEAST_ONCE)
    topic motion_off = ("motion/still",     AT_LEAST_ONCE)
    topic alert_warn = ("alerts/warning",   AT_LEAST_ONCE)

    sensor temp_sensor {
        type: TEMP_DS18B20
        gpioPin: 4
        // arithmetic sampleRate: 2 * 5 = 10 seconds
        sampleRate: 2 * 5 sec
        deployedOn: pi5
        trigger high_temp {
            // arithmetic threshold: 20 + 5 = 25
            when value > 20 + 5:
                publish temp_high: {value}
        }
        trigger low_temp {
            // arithmetic threshold: 5 * 5 = 25
            when value <= 5 * 5:
                publish temp_low: {value}
        }
    }

    sensor motion_sensor {
        type: MOTION_PIR
        gpioPin: 17
        // sampleRate 0 = interrupt-driven, no polling timer generated
        sampleRate: 0 sec
        deployedOn: pi5
        trigger motion_start {
            when value == 1:
                publish motion_on: {value}
        }
        trigger motion_stop {
            when value == 0:
                publish motion_off: {value}
        }
    }

    coordinator room_safety {
        deployedOn: pi4
        subscribeTo: temp_high, temp_low, motion_on, motion_off

        rule heat_and_motion {
            // 'and' has higher precedence than 'or' — no parens needed
            when temp_high and motion_on:
                publish alert_warn: {message: "High temp + motion nearby"}
        }

        rule complex_alert {
            // parentheses override precedence explicitly
            when (temp_high or temp_low) and not motion_off:
                publish alert_warn: {message: "Complex condition triggered"}
        }
    }

    actuator warning_led {
        type: LED
        gpioPin: 20
        deployedOn: pi4
        subscribeTo: temp_high, temp_low
        on message from temp_high: turn ON
        on message from temp_low:  turn OFF
    }

    actuator alarm_buzzer {
        type: BUZZER
        gpioPin: 18
        deployedOn: pi4
        subscribeTo: motion_on, motion_off
        // arithmetic duration: 2 * 5 = 10 seconds
        on message from motion_on:  turn ON for 2 * 5 sec
        on message from motion_off: turn OFF
    }
}
```

---

## Program 2: Smart Greenhouse Monitor

A two-node greenhouse system where soil moisture and temperature sensors trigger a water pump and status LED. Demonstrates arithmetic thresholds and a timed actuator for the water pump.

```
system Greenhouse {

    broker: "192.168.1.198" port: 1883

    node pi_sensor
    node pi_controller

    topic soil_dry  = ("soil/dry",          AT_LEAST_ONCE)
    topic soil_wet  = ("soil/wet",          AT_LEAST_ONCE)
    topic temp_hot  = ("temp/hot",          AT_LEAST_ONCE)
    topic temp_ok   = ("temp/normal",       AT_LEAST_ONCE)
    topic alert_gh  = ("alerts/greenhouse", AT_LEAST_ONCE)

    sensor soil_sensor {
        type: HUMIDITY
        gpioPin: 21
        // arithmetic sampleRate: 2 * 5 = 10 seconds
        sampleRate: 2 * 5 sec
        deployedOn: pi_sensor
        trigger needs_water {
            // arithmetic threshold: 10 * 3 = 30
            when value < 10 * 3:
                publish soil_dry: {value}
        }
        trigger has_water {
            when value >= 30:
                publish soil_wet: {value}
        }
    }

    sensor temp_sensor {
        type: TEMP_DS18B20
        gpioPin: 4
        sampleRate: 5 sec
        deployedOn: pi_sensor
        trigger too_hot {
            // arithmetic threshold: 30 + 5 = 35
            when value > 30 + 5:
                publish temp_hot: {value}
        }
        trigger normal_temp {
            when value <= 35:
                publish temp_ok: {value}
        }
    }

    coordinator greenhouse_brain {
        deployedOn: pi_controller
        subscribeTo: soil_dry, soil_wet, temp_hot, temp_ok

        rule dry_and_hot {
            when soil_dry and temp_hot:
                publish alert_gh: {message: "Dry soil + high temp"}
        }

        rule either_problem {
            // or condition — alert if either sensor is in bad state
            when soil_dry or temp_hot:
                publish alert_gh: {message: "Greenhouse needs attention"}
        }
    }

    actuator water_pump {
        type: RELAY
        gpioPin: 26
        deployedOn: pi_controller
        subscribeTo: soil_dry, soil_wet
        // arithmetic duration: 5 * 2 = 10 seconds of watering
        on message from soil_dry: turn ON for 5 * 2 sec
        on message from soil_wet: turn OFF
    }

    actuator status_led {
        type: LED
        gpioPin: 18
        deployedOn: pi_controller
        subscribeTo: temp_hot, temp_ok
        on message from temp_hot: turn ON
        on message from temp_ok:  turn OFF
    }
}
```

---

## Program 3: Door Security System

A single-node system where one Pi handles both sensing and actuation. A PIR motion sensor on the door triggers a buzzer and LED on the same device. Demonstrates a single-node deployment and complex `not` condition.

```
system DoorSecurity {

    broker: "192.168.1.198" port: 1883

    node security_pi

    topic door_open     = ("door/opened",      AT_LEAST_ONCE)
    topic door_close    = ("door/closed",       AT_LEAST_ONCE)
    topic alert_intruder = ("alerts/intruder",  AT_LEAST_ONCE)

    sensor door_sensor {
        type: MOTION_PIR
        gpioPin: 17
        // interrupt-driven — no polling timer
        sampleRate: 0 sec
        deployedOn: security_pi
        trigger door_opened {
            when value == 1:
                publish door_open: {value}
        }
        trigger door_closed {
            when value == 0:
                publish door_close: {value}
        }
    }

    coordinator door_monitor {
        deployedOn: security_pi
        subscribeTo: door_open, door_close

        rule intruder_alert {
            // not condition: alert when door is open (not closed)
            when not door_close:
                publish alert_intruder: {message: "Door opened - check entry"}
        }
    }

    actuator entry_buzzer {
        type: BUZZER
        gpioPin: 23
        deployedOn: security_pi
        subscribeTo: door_open, door_close
        // arithmetic duration: 2 * 1 = 2 seconds
        on message from door_open:  turn ON for 2 * 1 sec
        on message from door_close: turn OFF
    }

    actuator entry_led {
        type: LED
        gpioPin: 18
        deployedOn: security_pi
        subscribeTo: door_open, door_close
        on message from door_open:  turn ON
        on message from door_close: turn OFF
    }
}
```

---

## Generator Output Description

The code generator takes the DSL model and produces a complete set of Elixir/Nerves source files, grouped by deployment node. For every `node` defined in the system, the generator creates a separate folder under `src-gen/lib/<node_name>/` containing the following files:

### `application.ex`

The OTP Application supervisor. It wires up the MQTT broker connection via Tortoise311, injects the correct handler (a custom `MqttHandler` for coordinator nodes, the default logger for sensor-only nodes), lists all MQTT topic subscriptions derived from the coordinator's `subscribeTo` references, and starts all child GenServer processes using list concatenation (`++`) to avoid Elixir comma placement issues.

### `<sensor_name>.ex`

One GenServer per sensor. The generator branches on `sensor.type`:

- **`TEMP_DS18B20`** — generates a polling GenServer using `:timer.send_interval`. The interval is computed at generation time by evaluating the `sampleRate` arithmetic expression (`evalNumExpr`), so `2 * 5 sec` becomes `10000` directly in the generated code. The sensor reads the DS18B20 via the Linux 1-Wire file system (`/sys/bus/w1/devices/28-*/w1_slave`). The trigger threshold is also evaluated at generation time – `when value > 20 + 5` becomes `if value > 25`.
- **`MOTION_PIR`** – generates an interrupt-driven GenServer using `Circuits.GPIO.set_interrupts`. No polling timer is generated. The `sampleRate: 0 sec` value is a DSL convention signalling interrupt mode – the generator ignores the value and only emits `set_interrupts`. Each trigger maps to a `handle_info` function clause with a `when` guard.

### `<actuator_name>.ex`

One GenServer per actuator with `turn_on/0` and `turn_off/0` public API. It opens the GPIO pin as an output on startup and writes `1` or `0` in response to cast messages. If an `OnMessage` rule has a `for` duration, the generator emits an additional `handle_cast({:timed_on, topic})` clause that turns ON, calls `Process.sleep` for the evaluated duration in milliseconds, then turns OFF automatically. Duration arithmetic is also evaluated at generation time – `2 * 5 sec` becomes `Process.sleep(10000)`.

### `mqtt_handler.ex`

A `Tortoise311.Handler` implementation generated only for coordinator nodes. Each topic in `subscribeTo` produces one `handle_message/3` clause. The topic string is split on `/` to produce Elixir pattern match segments — `"temperature/high"` becomes `["temperature", "high"]`. Each clause calls `Coordinator.update_state(:temperature, :high)`.

### `coordinator.ex`

A stateful GenServer acting as a rule engine. The initial state map is built from unique sensor key names derived from the subscribed topics – `temp_high` and `temp_low` both contribute the key `:temperature`, so only one entry appears with a safe `:unknown` default. On every MQTT message the state is updated via `Map.put` and all rules are re-evaluated.

Rule conditions use the left-recursive boolean expression grammar (`BoolOr → BoolAnd → BoolNot → BoolAtom`) which produces a proper AST tree. The generator recursively traverses this tree:

- `BoolAndExpr` → `(left and right)` – `and` has higher precedence than `or`
- `BoolOrExpr` → `(left or right)`
- `BoolNotExpr` → `not (operand)` – parentheses ensure correct Elixir precedence
- `TopicRef` → `state.temperature == :high` – topic string split on `/`

This means complex DSL expressions like `when (temp_high or temp_low) and not motion_off:` generate correctly structured Elixir with proper operator precedence, without any hardcoded conditions in the generator.

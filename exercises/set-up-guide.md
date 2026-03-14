# Setup Guide
### Step 1 — Install Mosquitto broker on MacOS

```bash
brew install mosquitto
```

---

### Step 2 — Watch all MQTT messages (any machine on the network)

To find your Mac's IP address so your Raspberry Pis know where to look for the MQTT broker, run this command in your Mac terminal:

Bash

```
ipconfig getifaddr en0
```


Open a terminal and subscribe to all topics with:

```bash
mosquitto_sub -h 192.168.1.198 -t "#" -v
```

You will see live output like:

```
motion/detected true
temperature/high 26.5
alerts/warning {"message": "High temp + motion nearby"}
motion/still false
temperature/low 24.1
```

The `-t "#"` wildcard matches every topic. `-v` prints the topic name alongside the payload.

---

### Step 3 — Flash firmware to both Pis from your Mac

```bash
# Pi5 — sensor node
cd ~/nerves/pi5_node
export MIX_TARGET=rpi5
mix compile
mix firmware
mix upload pi5_node.local

# Pi4 — coordinator + actuator node
cd ~/nerves/pi4_node
export MIX_TARGET=rpi4
mix compile
mix firmware
mix upload pi4_node.local
```

Both Pis reboot automatically and start running the new code. 

---

### Step 4 — Monitor logs on each Pi

Open two terminals, one per Pi:

**Terminal 1 — Pi5 (sensors):**

```bash
ssh pi5_node.local
# In IEx:
RingLogger.attach()
```

You will see:

```
[warning] Motion detected! Publishing.
[info]    Motion cleared. Publishing still.
[warning] Temp crossed above 25°C. Publishing HIGH.
[info]    Temp dropped below 25°C. Publishing LOW.
```

**Terminal 2 — Pi4 (coordinator + actuators):**

```bash
ssh pi4_node.local
# In IEx:
RingLogger.attach()
```

You will see:

```
[error] ALERT: High temp + motion nearby
```

---

### Step 5 — Trigger the system manually for testing

From any machine on the network you can simulate sensor events without touching the hardware:

```bash
# Simulate motion detected
mosquitto_pub -h 192.168.1.198 -t "motion/detected" -m "true"
# → Buzzer turns ON on Pi4

# Simulate motion cleared
mosquitto_pub -h 192.168.1.198 -t "motion/still" -m "false"
# → Buzzer turns OFF on Pi4

# Simulate high temperature
mosquitto_pub -h 192.168.1.198 -t "temperature/high" -m "27.3"
# → LED turns ON on Pi4

# Simulate both conditions → triggers coordinator rule
mosquitto_pub -h 192.168.1.198 -t "temperature/high" -m "27.3"
mosquitto_pub -h 192.168.1.198 -t "motion/detected" -m "true"
# → LED ON + Buzzer ON + alert published to alerts/warning
```

---

### Full System Architecture

```
Pi5 (Nerves — sensor node)
  DS18B20  → GPIO 4  → TempSensor GenServer
  HC-SR501 → GPIO 17 → MotionSensor GenServer
      │
      │  publishes via MQTT to broker on Pi4
      ▼
  192.168.1.198:1883  (Mosquitto broker on Pi4)
      │
      │  delivers messages to subscribers
      ▼
Pi4 (Nerves — coordinator + actuator node)
  MqttHandler → Coordinator (rule engine)
                    │
                    ├── WarningLed  → GPIO 20 → LED
                    └── AlarmBuzzer → GPIO 18 → Buzzer
```

### Pasting generated code into the projects

```bash
# ── Pi5 — sensor node ──────────────────────────────────────────────────────
cp /Users/artemziablov/runtime-EclipseApplication/MultiSensorSafetyAlert/src-gen/lib/pi5/temp_sensor.ex \
   ~/nerves/pi5_node/lib/pi5_node/

cp /Users/artemziablov/runtime-EclipseApplication/MultiSensorSafetyAlert/src-gen/lib/pi5/motion_sensor.ex \
   ~/nerves/pi5_node/lib/pi5_node/

cp /Users/artemziablov/runtime-EclipseApplication/MultiSensorSafetyAlert/src-gen/lib/pi5/application.ex \
   ~/nerves/pi5_node/lib/pi5_node/

# ── Pi4 — coordinator + actuator node ─────────────────────────────────────
cp /Users/artemziablov/runtime-EclipseApplication/MultiSensorSafetyAlert/src-gen/lib/pi4/application.ex \
   ~/nerves/pi4_node/lib/pi4_node/

cp /Users/artemziablov/runtime-EclipseApplication/MultiSensorSafetyAlert/src-gen/lib/pi4/coordinator.ex \
   ~/nerves/pi4_node/lib/pi4_node/

cp /Users/artemziablov/runtime-EclipseApplication/MultiSensorSafetyAlert/src-gen/lib/pi4/mqtt_handler.ex \
   ~/nerves/pi4_node/lib/pi4_node/

cp /Users/artemziablov/runtime-EclipseApplication/MultiSensorSafetyAlert/src-gen/lib/pi4/warning_led.ex \
   ~/nerves/pi4_node/lib/pi4_node/

cp /Users/artemziablov/runtime-EclipseApplication/MultiSensorSafetyAlert/src-gen/lib/pi4/alarm_buzzer.ex \
   ~/nerves/pi4_node/lib/pi4_node/

# ── Delete old hand-written files replaced by generated ones ───────────────
rm ~/nerves/pi4_node/lib/pi4_node/led.ex
rm ~/nerves/pi4_node/lib/pi4_node/buzzer.ex

# ── Build and deploy Pi5 ───────────────────────────────────────────────────
cd ~/nerves/pi5_node
export MIX_TARGET=rpi5
mix firmware
mix upload pi5_node.local

# ── Build and deploy Pi4 ───────────────────────────────────────────────────
cd ~/nerves/pi4_node
export MIX_TARGET=rpi4
mix firmware
mix upload pi4_node.local
```

defmodule Pi5Node.MotionSensor do
  use GenServer
  require Logger

  def start_link(_),
    do: GenServer.start_link(__MODULE__, nil, name: __MODULE__)

  # ── HC-SR501 PIR Motion Sensor (interrupt-driven) ─────────────────
  # No polling — GPIO 17 interrupt fires instantly on change

  def init(_) do
    {:ok, gpio} = Circuits.GPIO.open(17, :input)
    Circuits.GPIO.set_interrupts(gpio, :both)
    Logger.info("motion_sensor: listening on GPIO 17")
    {:ok, gpio}
  end

  # Trigger motion_start: when pin_value == 1
  def handle_info({:circuits_gpio, 17, _timestamp, pin_value}, state)
      when pin_value == 1 do
    Logger.info("motion_sensor: motion_start fired (value=#{pin_value})")
    Tortoise311.publish(
      "pi5_sensor_node",
      "motion/detected",
      "#{pin_value}",
      qos: 1
    )
    {:noreply, state}
  end

  # Trigger motion_stop: when pin_value == 0
  def handle_info({:circuits_gpio, 17, _timestamp, pin_value}, state)
      when pin_value == 0 do
    Logger.info("motion_sensor: motion_stop fired (value=#{pin_value})")
    Tortoise311.publish(
      "pi5_sensor_node",
      "motion/still",
      "#{pin_value}",
      qos: 1
    )
    {:noreply, state}
  end


  def handle_info(_message, state), do: {:noreply, state}
end

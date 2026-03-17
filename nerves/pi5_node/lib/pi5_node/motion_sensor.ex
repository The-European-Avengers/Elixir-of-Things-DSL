defmodule Pi5Node.MotionSensor do
  use GenServer
  require Logger

  def start_link(_),
    do: GenServer.start_link(__MODULE__, nil, name: __MODULE__)

  def init(_) do
    {:ok, gpio} = Circuits.GPIO.open(17, :input)
    Circuits.GPIO.set_interrupts(gpio, :both)
    {:ok, gpio}
  end

  def handle_info({:circuits_gpio, 17, _timestamp, 1}, state) do
    Logger.warning("Motion detected! Publishing.")
    Tortoise311.publish(
      "pi5_sensor_node",
      "motion/detected",
      "true",
      qos: 1
    )
    {:noreply, state}
  end

  def handle_info({:circuits_gpio, 17, _timestamp, 0}, state) do
    Logger.info("Motion cleared. Publishing still.")
    Tortoise311.publish(
      "pi5_sensor_node",
      "motion/still",
      "false",
      qos: 1
    )
    {:noreply, state}
  end

  def handle_info(_message, state), do: {:noreply, state}
end

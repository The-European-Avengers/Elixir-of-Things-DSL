defmodule Pi4Node.AlarmBuzzer do
  use GenServer
  require Logger

  def turn_on(),  do: GenServer.cast(__MODULE__, :turn_on)
  def turn_off(), do: GenServer.cast(__MODULE__, :turn_off)

  def start_link(_),
    do: GenServer.start_link(__MODULE__, nil, name: __MODULE__)

  def init(_) do
    {:ok, gpio} = Circuits.GPIO.open(18, :output)
    Circuits.GPIO.write(gpio, 0)
    Logger.info("alarm_buzzer: ready on GPIO 18")
    {:ok, gpio}
  end

  def handle_cast(:turn_on, gpio) do
    Circuits.GPIO.write(gpio, 1)
    Logger.info("alarm_buzzer: ON")
    {:noreply, gpio}
  end

  def handle_cast(:turn_off, gpio) do
    Circuits.GPIO.write(gpio, 0)
    Logger.info("alarm_buzzer: OFF")
    {:noreply, gpio}
  end

  # on message from motion_on: turn ON for 10 sec = 10000 ms
  def handle_cast({:timed_on, "motion/detected"}, gpio) do
    Circuits.GPIO.write(gpio, 1)
    Logger.info("alarm_buzzer: ON for 10000ms")
    Process.sleep(10000)
    Circuits.GPIO.write(gpio, 0)
    Logger.info("alarm_buzzer: OFF (timer expired)")
    {:noreply, gpio}
  end

end

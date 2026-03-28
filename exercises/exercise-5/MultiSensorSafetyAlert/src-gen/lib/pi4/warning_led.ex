defmodule Pi4Node.WarningLed do
  use GenServer
  require Logger

  def turn_on(),  do: GenServer.cast(__MODULE__, :turn_on)
  def turn_off(), do: GenServer.cast(__MODULE__, :turn_off)

  def start_link(_),
    do: GenServer.start_link(__MODULE__, nil, name: __MODULE__)

  def init(_) do
    {:ok, gpio} = Circuits.GPIO.open(20, :output)
    Circuits.GPIO.write(gpio, 0)
    Logger.info("warning_led: ready on GPIO 20")
    {:ok, gpio}
  end

  def handle_cast(:turn_on, gpio) do
    Circuits.GPIO.write(gpio, 1)
    Logger.info("warning_led: ON")
    {:noreply, gpio}
  end

  def handle_cast(:turn_off, gpio) do
    Circuits.GPIO.write(gpio, 0)
    Logger.info("warning_led: OFF")
    {:noreply, gpio}
  end

end

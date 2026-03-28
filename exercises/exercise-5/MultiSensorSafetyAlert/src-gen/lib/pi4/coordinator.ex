defmodule Pi4Node.Coordinator do
  use GenServer
  require Logger

  def update_state(sensor, value) do
    GenServer.cast(__MODULE__, {:update, sensor, value})
  end

  def start_link(_),
    do: GenServer.start_link(__MODULE__, nil, name: __MODULE__)

  def init(_) do
    initial = %{
      temperature: :unknown,
      motion: :unknown
    }
    Logger.info("Coordinator started: #{inspect(initial)}")
    {:ok, initial}
  end

  def handle_cast({:update, sensor, value}, state) do
    new_state = Map.put(state, sensor, value)
    Logger.debug("Coordinator state: #{inspect(new_state)}")
    evaluate_rules(new_state)
    {:noreply, new_state}
  end

  defp evaluate_rules(state) do
    # Rule: heat_and_motion
    if (state.temperature == :high and state.motion == :detected) do
      Logger.error("ALERT [heat_and_motion]: High temp + motion nearby")
      Tortoise311.publish(
        "pi4_coordinator_node",
        "alerts/warning",
        ~s({"message": "High temp + motion nearby"}),
        qos: 1
      )
    end
    # Rule: complex_alert
    if ((state.temperature == :high or state.temperature == :low) and not (state.motion == :still)) do
      Logger.error("ALERT [complex_alert]: Complex condition triggered")
      Tortoise311.publish(
        "pi4_coordinator_node",
        "alerts/warning",
        ~s({"message": "Complex condition triggered"}),
        qos: 1
      )
    end

    # warning_led (LED on GPIO 20)
    if state.temperature == :high,
      do: Pi4Node.WarningLed.turn_on(),
      else: Pi4Node.WarningLed.turn_off()
    # alarm_buzzer (BUZZER on GPIO 18)
    if state.motion == :detected,
      do: Pi4Node.AlarmBuzzer.turn_on(),
      else: Pi4Node.AlarmBuzzer.turn_off()
  end
end

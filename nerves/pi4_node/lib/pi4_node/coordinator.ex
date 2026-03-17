defmodule Pi4Node.Coordinator do
  use GenServer
  require Logger

  def update_state(sensor, value) do
    GenServer.cast(__MODULE__, {:update, sensor, value})
  end

  def start_link(_),
    do: GenServer.start_link(__MODULE__, nil, name: __MODULE__)

  def init(_) do
    {:ok, %{temperature: :low, motion: :still}}
  end

  def handle_cast({:update, sensor, value}, state) do
    new_state = Map.put(state, sensor, value)
    evaluate_rules(new_state)
    {:noreply, new_state}
  end

  defp evaluate_rules(state) do
    # DSL Rule: heat_and_motion
    if state.temperature == :high and state.motion == :detected do
      Logger.error("ALERT: High temp + motion nearby")
      Tortoise311.publish(
        "pi4_coordinator_node",
        "alerts/warning",
        ~s({"message": "High temp + motion nearby"}),
        qos: 1
      )
    end

    if state.temperature == :high,
      do: Pi4Node.WarningLed.turn_on(),
      else: Pi4Node.WarningLed.turn_off()
    if state.motion == :detected,
      do: Pi4Node.AlarmBuzzer.turn_on(),
      else: Pi4Node.AlarmBuzzer.turn_off()
  end
end

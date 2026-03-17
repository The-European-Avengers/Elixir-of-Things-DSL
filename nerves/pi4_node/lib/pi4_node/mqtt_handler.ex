defmodule Pi4Node.MqttHandler do
  use Tortoise311.Handler
  require Logger

  def init(args), do: {:ok, args}
  def connection(_status, state), do: {:ok, state}

  def handle_message(["temperature", "high"], _payload, state) do
    Pi4Node.Coordinator.update_state(
      :temperature,
      :high
    )
    {:ok, state}
  end

  def handle_message(["temperature", "low"], _payload, state) do
    Pi4Node.Coordinator.update_state(
      :temperature,
      :low
    )
    {:ok, state}
  end

  def handle_message(["motion", "detected"], _payload, state) do
    Pi4Node.Coordinator.update_state(
      :motion,
      :detected
    )
    {:ok, state}
  end

  def handle_message(["motion", "still"], _payload, state) do
    Pi4Node.Coordinator.update_state(
      :motion,
      :still
    )
    {:ok, state}
  end

  def subscription(_status, _topic_filter, state), do: {:ok, state}
  def terminate(_reason, _state), do: :ok
end

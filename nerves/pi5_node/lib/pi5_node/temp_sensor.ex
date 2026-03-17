defmodule Pi5Node.TempSensor do
  use GenServer
  require Logger

  def start_link(_),
    do: GenServer.start_link(__MODULE__, nil, name: __MODULE__)

  def init(_) do
    :timer.send_interval(5000, :read_temp)
    {:ok, %{last_state: :low}}
  end

  def handle_info(:read_temp, state) do
    case Path.wildcard("/sys/bus/w1/devices/28-*") do
      [sensor_path | _] ->
        case File.read(Path.join(sensor_path, "w1_slave")) do
          {:ok, data} ->
            case Regex.run(~r/t=(-?\d+)/, data) do
              [_, temp_string] ->
                temp_c = String.to_integer(temp_string) / 1000.0
                current = if temp_c > 25.0, do: :high, else: :low

                if current != state.last_state do
                  topic = if current == :high,
                    do: "temperature/high",
                    else: "temperature/low"
                  Tortoise311.publish(
                    "pi5_sensor_node",
                    topic,
                    "#{temp_c}",
                    qos: 1
                  )
                end
                {:noreply, %{last_state: current}}

              _ -> {:noreply, state}
            end
          _ -> {:noreply, state}
        end
      [] -> {:noreply, state}
    end
  end


  def handle_info(_message, state), do: {:noreply, state}
end

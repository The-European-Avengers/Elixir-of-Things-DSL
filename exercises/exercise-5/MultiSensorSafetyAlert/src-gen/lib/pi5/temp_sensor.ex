defmodule Pi5Node.TempSensor do
  use GenServer
  require Logger

  def start_link(_),
    do: GenServer.start_link(__MODULE__, nil, name: __MODULE__)

  # ── DS18B20 Temperature Sensor (1-Wire) ───────────────────────────
  # Polls every 5 sec
  # GPIO 4 — requires dtoverlay=w1-gpio in /boot/config.txt

  def init(_) do
    :timer.send_interval(5000, :read_sensor)
    {:ok, %{last_value: nil}}
  end

  def handle_info(:read_sensor, state) do
    case Path.wildcard("/sys/bus/w1/devices/28-*") do
      [sensor_path | _] ->
        case File.read(Path.join(sensor_path, "w1_slave")) do
          {:ok, data} ->
            case Regex.run(~r/t=(-?\d+)/, data) do
              [_, raw] ->
                value = String.to_integer(raw) / 1000.0
                Logger.debug("temp_sensor: #{value}°C")
                # Trigger high_temp: when value > 25
                if value > 25 do
                  Tortoise311.publish(
                    "pi5_sensor_node",
                    "temperature/high",
                    "#{value}",
                    qos: 1
                  )
                end
                # Trigger low_temp: when value <= 25
                if value <= 25 do
                  Tortoise311.publish(
                    "pi5_sensor_node",
                    "temperature/low",
                    "#{value}",
                    qos: 1
                  )
                end
                {:noreply, %{last_value: value}}
              _ ->
                Logger.warning("temp_sensor: failed to parse data")
                {:noreply, state}
            end
          {:error, reason} ->
            Logger.warning("temp_sensor: read error #{inspect(reason)}")
            {:noreply, state}
        end
      [] ->
        Logger.warning("temp_sensor: no 1-Wire device found")
        {:noreply, state}
    end
  end


  # Catch-all — prevents crashes from unmatched messages
  def handle_info(_message, state), do: {:noreply, state}
end

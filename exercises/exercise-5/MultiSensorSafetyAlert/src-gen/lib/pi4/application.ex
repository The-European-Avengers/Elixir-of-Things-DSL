defmodule Pi4Node.Application do
  use Application

  def start(_type, _args) do
    tortoise_opts = [
      client_id: "pi4_coordinator_node",
      server: {Tortoise311.Transport.Tcp,
               host: "192.168.1.198",
               port: 1883},
      handler: {Pi4Node.MqttHandler, []},
      subscriptions: [
        {"temperature/high", 1},
        {"temperature/low", 1},
        {"motion/detected", 1},
        {"motion/still", 1}
      ]
    ]

    children =
      [{Tortoise311.Connection, tortoise_opts}]
      ++ [Pi4Node.WarningLed]
      ++ [Pi4Node.AlarmBuzzer]
      ++ [Pi4Node.Coordinator]

    Supervisor.start_link(children,
      strategy: :one_for_one,
      name: Pi4Node.Supervisor)
  end
end

    defmodule Pi5Node.Application do
      use Application

      def start(_type, _args) do
        tortoise_opts = [
          client_id: "pi5_sensor_node",
          server: {Tortoise311.Transport.Tcp,
                   host: "192.168.1.198",
                   port: 1883},
          handler: {Tortoise311.Handler.Logger, []}        ]

        children =
          [{Tortoise311.Connection, tortoise_opts}]
          ++ [Pi5Node.TempSensor]
          ++ [Pi5Node.MotionSensor]

        Supervisor.start_link(children,
          strategy: :one_for_one,
          name: Pi5Node.Supervisor)
      end
    end

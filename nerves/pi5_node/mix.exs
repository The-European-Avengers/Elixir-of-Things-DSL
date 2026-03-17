defmodule Pi5Node.MixProject do
  use Mix.Project

  @app :pi5_node
  @version "0.1.0"
  @all_targets [:bbb, :grisp2, :osd32mp1, :mangopi_mq_pro, :qemu_aarch64, :rpi, :rpi0, :rpi0_2, :rpi2, :rpi3, :rpi4, :rpi5, :x86_64]

  def project do
    [
      app: @app,
      version: @version,
      elixir: "~> 1.19",
      archives: [nerves_bootstrap: "~> 1.15"],
      listeners: listeners(Mix.target(), Mix.env()),
      start_permanent: Mix.env() == :prod,
      deps: deps(),
      releases: [{@app, release()}]
    ]
  end

  # Run "mix help compile.app" to learn about applications.
  def application do
    [
      extra_applications: [:logger, :runtime_tools],
      mod: {Pi5Node.Application, []}
    ]
  end

  def cli do
    [preferred_targets: [run: :host, test: :host]]
  end

  # Run "mix help deps" to learn about dependencies.
  defp deps do
    [
    # Core Nerves
    {:nerves, "~> 1.10", runtime: false},
    {:shoehorn, "~> 0.9"},
    {:ring_logger, "~> 0.11"},
    {:toolshed, "~> 0.4"},

    # Nerves pack - handles SSH, networking, mDNS etc.
    {:nerves_pack, "~> 0.7"},

    # MQTT client
    {:tortoise311, "~> 0.12"},

    # GPIO
    {:circuits_gpio, "~> 2.0"},

    # Target system — rpi5 specifically
    {:nerves_system_rpi5, "~> 0.6", runtime: false, targets: :rpi5},
  ]
  end

  def release do
    [
      overwrite: true,
      # Erlang distribution is not started automatically.
      # See https://hexdocs.pm/nerves_pack/readme.html#erlang-distribution
      cookie: "#{@app}_cookie",
      include_erts: &Nerves.Release.erts/0,
      steps: [&Nerves.Release.init/1, :assemble],
      strip_beams: Mix.env() == :prod or [keep: ["Docs"]]
    ]
  end

  # Uncomment the following line if using Phoenix > 1.8.
  # defp listeners(:host, :dev), do: [Phoenix.CodeReloader]
  defp listeners(_, _), do: []
end

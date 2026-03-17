defmodule Pi4NodeTest do
  use ExUnit.Case
  doctest Pi4Node

  test "greets the world" do
    assert Pi4Node.hello() == :world
  end
end

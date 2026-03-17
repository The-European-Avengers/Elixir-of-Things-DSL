defmodule Pi5NodeTest do
  use ExUnit.Case
  doctest Pi5Node

  test "greets the world" do
    assert Pi5Node.hello() == :world
  end
end

![Meta-model Diagram](doc/img/meta-model.png)

## Syntax

system SmartGreenhouse {
  pubsub: "Greenhouse.PubSub"
  
  // Define nodes (Raspberry Pis)
  node pi4 {
    name: "pi4@192.168.1.100"
    ipAddress: "192.168.1.100"
  }
  
  node pi5 {
    name: "pi5@192.168.1.101"
    ipAddress: "192.168.1.101"
  }
  
  // Define channels
  channel "temperature:reading"
  channel "temperature:high"
  channel "fan:command"
  
  // Temperature sensor
  sensor temp_sensor {
    type: TEMPERATURE
    gpioPin: 4
    sampleRate: 5 seconds
    deployedOn: pi4
    
    publishTo: "temperature:reading"
    
    trigger high_temp {
      when value > 25:
        broadcast "temperature:high": {value, timestamp}
    }
  }
  
  // Cooling fan actuator
  actuator cooling_fan {
    type: RELAY
    gpioPin: 18
    deployedOn: pi5
    
    subscribeTo: "fan:command"
  }
  
  // Coordinator
  coordinator climate_control {
    deployedOn: pi4
    
    subscribeTo: "temperature:high"
    
    when message received:
      activate cooling_fan for 2 minutes
  }
}




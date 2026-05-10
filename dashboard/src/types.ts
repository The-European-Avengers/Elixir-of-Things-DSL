export type DashboardConnectionStatus = 'idle' | 'connecting' | 'connected' | 'disconnected' | 'error'

export interface DashboardBroker {
  host: string
  port: number
  wsPort?: number
  protocol?: string
  wsProtocol?: 'ws' | 'wss'
}

export interface DashboardNode {
  id: string
  name: string
}

export interface DashboardSensor {
  id: string
  name: string
  type: string
  gpioPin: number
  sampleRateMs: number
  deployedOnNodeId: string
}

export interface DashboardActuator {
  id: string
  name: string
  type: string
  gpioPin: number
  deployedOnNodeId: string
  subscribeToTopicIds?: string[]
}

export interface DashboardCoordinator {
  id: string
  name: string
  deployedOnNodeId: string
  subscribeToTopicIds: string[]
}

export interface DashboardTopology {
  systemName: string
  broker: DashboardBroker
  nodes: DashboardNode[]
  sensors: DashboardSensor[]
  actuators: DashboardActuator[]
  coordinators: DashboardCoordinator[]
}

export interface TopicDefinition {
  id: string
  name: string
  path: string
  qos: number
  payloadType?: string
  unit?: string | null
  producers?: string[]
  consumers?: string[]
}

export interface DashboardTopics {
  systemName: string
  broker: DashboardBroker
  topics: TopicDefinition[]
}

export interface DashboardMessageEvent {
  topic: string
  payloadText: string
  parsedPayload: unknown
  timestamp: string
  qos?: number
  retained?: boolean
}
import type { DashboardTopology, DashboardTopics } from '../types'

export type DiagramLane = 'source' | 'logic' | 'sink'

export type NodeSummary = {
  node: DashboardTopology['nodes'][number]
  sensors: DashboardTopology['sensors']
  actuators: DashboardTopology['actuators']
  coordinators: DashboardTopology['coordinators']
  lane: DiagramLane
  inboundTopics: string[]
  outboundTopics: string[]
  inboundTopicDetails: TopicConnectionSummary[]
  outboundTopicDetails: TopicConnectionSummary[]
}

export type TopicConnectionSummary = {
  id: string
  name: string
  path: string
  qos: number
  connectedNodes: string[]
}

function uniqueStrings(values: Array<string | undefined | null>): string[] {
  return Array.from(new Set(values.filter((value): value is string => Boolean(value))))
}

function buildTopicConnectionSummaries(
  connectionNodesByPath: Map<string, Set<string>>,
  topicByPath: Map<string, DashboardTopics['topics'][number]>,
  nodeNameById: Map<string, string>,
): TopicConnectionSummary[] {
  return Array.from(connectionNodesByPath.entries())
    .sort(([a], [b]) => a.localeCompare(b))
    .map(([path, nodeIds]) => {
      const topic = topicByPath.get(path)
      if (!topic) return null

      const connectedNodes = Array.from(
        new Set(
          Array.from(nodeIds)
            .map((nodeId) => nodeNameById.get(nodeId))
            .filter((nodeName): nodeName is string => Boolean(nodeName)),
        ),
      ).sort((a, b) => a.localeCompare(b))

      return {
        id: topic.id,
        name: topic.name,
        path: topic.path,
        qos: topic.qos,
        connectedNodes,
      }
    })
    .filter((entry): entry is TopicConnectionSummary => Boolean(entry))
}

export function classifyNodeLane(node: {
  sensors: DashboardTopology['sensors']
  actuators: DashboardTopology['actuators']
  coordinators: DashboardTopology['coordinators']
}): DiagramLane {
  if (node.coordinators.length > 0 || (node.sensors.length > 0 && node.actuators.length > 0)) {
    return 'logic'
  }

  if (node.actuators.length > 0) {
    return 'sink'
  }

  return 'source'
}

export function buildNodeSummaries(
  topology: DashboardTopology,
  topics: DashboardTopics,
): NodeSummary[] {
  const deviceNodeById = new Map<string, string>()
  const nodeNameById = new Map<string, string>()
  const topicByPath = new Map<string, DashboardTopics['topics'][number]>()

  topology.nodes.forEach((node) => {
    nodeNameById.set(node.id, node.name)
  })

  topology.sensors.forEach((sensor) => {
    deviceNodeById.set(sensor.id, sensor.deployedOnNodeId)
  })
  topology.actuators.forEach((actuator) => {
    deviceNodeById.set(actuator.id, actuator.deployedOnNodeId)
  })
  topology.coordinators.forEach((coordinator) => {
    deviceNodeById.set(coordinator.id, coordinator.deployedOnNodeId)
  })

  topics.topics.forEach((topic) => {
    topicByPath.set(topic.path, topic)
  })

  const inboundByNode = new Map<string, Set<string>>()
  const outboundByNode = new Map<string, Set<string>>()
  const inboundDetailsByNode = new Map<string, Map<string, Set<string>>>()
  const outboundDetailsByNode = new Map<string, Map<string, Set<string>>>()

  topology.nodes.forEach((node) => {
    inboundByNode.set(node.id, new Set<string>())
    outboundByNode.set(node.id, new Set<string>())
    inboundDetailsByNode.set(node.id, new Map<string, Set<string>>())
    outboundDetailsByNode.set(node.id, new Map<string, Set<string>>())
  })

  topics.topics.forEach((topic) => {
    const producerNodeIds = uniqueStrings((topic.producers ?? []).map((deviceId) => deviceNodeById.get(deviceId)))
    const consumerNodeIds = uniqueStrings((topic.consumers ?? []).map((deviceId) => deviceNodeById.get(deviceId)))

    producerNodeIds.forEach((fromNodeId) => {
      outboundByNode.get(fromNodeId)?.add(topic.path)
      const outboundDetails = outboundDetailsByNode.get(fromNodeId)
      if (outboundDetails) {
        const consumers = outboundDetails.get(topic.path) ?? new Set<string>()
        consumerNodeIds.forEach((consumerNodeId) => consumers.add(consumerNodeId))
        outboundDetails.set(topic.path, consumers)
      }

      consumerNodeIds.forEach((toNodeId) => {
        inboundByNode.get(toNodeId)?.add(topic.path)

        const inboundDetails = inboundDetailsByNode.get(toNodeId)
        if (inboundDetails) {
          const producers = inboundDetails.get(topic.path) ?? new Set<string>()
          producerNodeIds.forEach((producerNodeId) => producers.add(producerNodeId))
          inboundDetails.set(topic.path, producers)
        }
      })
    })
  })

  return topology.nodes.map((node) => {
    const sensors = topology.sensors.filter((sensor) => sensor.deployedOnNodeId === node.id)
    const actuators = topology.actuators.filter((actuator) => actuator.deployedOnNodeId === node.id)
    const coordinators = topology.coordinators.filter(
      (coordinator) => coordinator.deployedOnNodeId === node.id,
    )

    return {
      node,
      sensors,
      actuators,
      coordinators,
      lane: classifyNodeLane({ sensors, actuators, coordinators }),
      inboundTopics: Array.from(inboundByNode.get(node.id) ?? []),
      outboundTopics: Array.from(outboundByNode.get(node.id) ?? []),
      inboundTopicDetails: buildTopicConnectionSummaries(
        inboundDetailsByNode.get(node.id) ?? new Map<string, Set<string>>(),
        topicByPath,
        nodeNameById,
      ),
      outboundTopicDetails: buildTopicConnectionSummaries(
        outboundDetailsByNode.get(node.id) ?? new Map<string, Set<string>>(),
        topicByPath,
        nodeNameById,
      ),
    }
  })
}


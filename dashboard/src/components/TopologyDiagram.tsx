import { useMemo, useState } from 'react'
import type { DashboardTopology, DashboardTopics } from '../types'
import { classifyNodeLane } from '../utils/topology'
import './../App.css'

type DiagramLane = 'source' | 'logic' | 'sink'

type DiagramNode = {
  node: DashboardTopology['nodes'][number]
  sensors: DashboardTopology['sensors']
  actuators: DashboardTopology['actuators']
  coordinators: DashboardTopology['coordinators']
  lane: DiagramLane
  inboundTopics: string[]
  outboundTopics: string[]
  position: { x: number; y: number }
}

type DiagramEdge = {
  id: string
  fromNodeId: string
  toNodeId: string
  topics: string[]
}

type TopicRouteSummary = {
  id: string
  name: string
  path: string
  qos: number
  fromNodes: string[]
  toNodes: string[]
  producersByNode: NodeDeviceMap
  consumersByNode: NodeDeviceMap
}

type DeviceRef = {
  id: string
  name: string
  type: 'sensor' | 'actuator' | 'coordinator'
}

type NodeDeviceMap = Record<string, DeviceRef[]>

function uniqueStrings(values: Array<string | undefined | null>): string[] {
  return Array.from(new Set(values.filter((v): v is string => Boolean(v))))
}

function clamp(value: number, min: number, max: number): number {
  return Math.min(max, Math.max(min, value))
}

function laneLabel(lane: DiagramLane): string {
  switch (lane) {
    case 'source':
      return 'Sensor nodes'
    case 'logic':
      return 'Logic nodes'
    case 'sink':
      return 'Actuator nodes'
  }
}

function layoutLanePosition(lane: DiagramLane, index: number, total: number, xByLane: Record<DiagramLane, number>): { x: number; y: number } {
  if (total <= 1) return { x: xByLane[lane], y: 50 }
  const bandTop = 16
  const bandBottom = 84
  const bandHeight = bandBottom - bandTop
  const step = bandHeight / (total + 1)
  const yBase = bandTop + step * (index + 1)
  const nudge = index % 2 === 0 ? -3 : 3
  return { x: xByLane[lane], y: clamp(yBase + nudge, bandTop, bandBottom) }
}

function buildTopologyDiagram(topology: DashboardTopology, topics: DashboardTopics) {
  const deviceNodeById = new Map<string, string>()
  topology.sensors.forEach((s) => deviceNodeById.set(s.id, s.deployedOnNodeId))
  topology.actuators.forEach((a) => deviceNodeById.set(a.id, a.deployedOnNodeId))
  topology.coordinators.forEach((c) => deviceNodeById.set(c.id, c.deployedOnNodeId))

  const edgeMap = new Map<string, DiagramEdge>()
  const inboundByNode = new Map<string, Set<string>>()
  const outboundByNode = new Map<string, Set<string>>()

  topology.nodes.forEach((n) => {
    inboundByNode.set(n.id, new Set())
    outboundByNode.set(n.id, new Set())
  })

  topics.topics.forEach((topic) => {
    const producerNodeIds = uniqueStrings((topic.producers ?? []).map((id) => deviceNodeById.get(id)))
    const consumerNodeIds = uniqueStrings((topic.consumers ?? []).map((id) => deviceNodeById.get(id)))

    producerNodeIds.forEach((from) => {
      outboundByNode.get(from)?.add(topic.path)
      consumerNodeIds.forEach((to) => {
        inboundByNode.get(to)?.add(topic.path)
        if (from === to) return
        const key = `${from}→${to}`
        const existing = edgeMap.get(key)
        if (existing) existing.topics.push(topic.path)
        else edgeMap.set(key, { id: key, fromNodeId: from, toNodeId: to, topics: [topic.path] })
      })
    })
  })

  const nodes = topology.nodes.map((node) => {
    const sensors = topology.sensors.filter((s) => s.deployedOnNodeId === node.id)
    const actuators = topology.actuators.filter((a) => a.deployedOnNodeId === node.id)
    const coordinators = topology.coordinators.filter((c) => c.deployedOnNodeId === node.id)
    return {
      node,
      sensors,
      actuators,
      coordinators,
      lane: classifyNodeLane({ sensors, actuators, coordinators }),
      inboundTopics: Array.from(inboundByNode.get(node.id) ?? []),
      outboundTopics: Array.from(outboundByNode.get(node.id) ?? []),
    }
  }) as Array<Omit<DiagramNode, 'position'>>

  const laneBuckets: Record<DiagramLane, Array<Omit<DiagramNode, 'position'>>> = { source: [], logic: [], sink: [] }
  nodes.forEach((n) => laneBuckets[n.lane].push(n))

  const xByLane: Record<DiagramLane, number> = { source: 18, logic: 50, sink: 82 }
  const occupied = { source: laneBuckets.source.length > 0, logic: laneBuckets.logic.length > 0, sink: laneBuckets.sink.length > 0 }
  if (occupied.source && occupied.logic && !occupied.sink) {
    xByLane.source = 20
    xByLane.logic = 64
  } else if (!occupied.source && occupied.logic && occupied.sink) {
    xByLane.logic = 36
    xByLane.sink = 80
  } else if (occupied.source && occupied.sink && !occupied.logic) {
    xByLane.source = 28
    xByLane.sink = 72
  } else if (!occupied.logic) {
    xByLane.source = 28
    xByLane.sink = 72
  }

  // assign positions
  const ordered: DiagramNode[] = (['source', 'logic', 'sink'] as const).flatMap((lane) =>
    laneBuckets[lane].map((entry, idx) => ({ ...entry, position: layoutLanePosition(lane, idx, laneBuckets[lane].length, xByLane) })),
  )

  const maxInLane = Math.max(laneBuckets.source.length, laneBuckets.logic.length, laneBuckets.sink.length)
  const canvasHeight = Math.max(480, 180 + maxInLane * 160)

  return { nodes: ordered, edges: Array.from(edgeMap.values()).sort((a, b) => a.id.localeCompare(b.id)), canvasHeight }
}

function edgeLabel(topics: string[]) {
  if (!topics.length) return 'signal'
  if (topics.length === 1) return topics[0]
  return `${topics.length} topics`
}

function routeEdgePath(
  fromX: number,
  fromY: number,
  toX: number,
  toY: number,
): string {
  const direction = toX >= fromX ? 1 : -1
  const nodeHalfWidth = 120
  const startX = fromX + direction * nodeHalfWidth
  const endX = toX - direction * nodeHalfWidth
  const horizontalDistance = Math.max(Math.abs(endX - startX), 80)
  const bend = Math.min(Math.max(horizontalDistance * 0.35, 90), 220)
  return `M ${startX} ${fromY} C ${startX + direction * bend} ${fromY}, ${endX - direction * bend} ${toY}, ${endX} ${toY}`
}

function buildTopicRouteSummaries(topology: DashboardTopology, topics: DashboardTopics): TopicRouteSummary[] {
  const deviceNodeById = new Map<string, string>()
  const nodeNameById = new Map<string, string>()
  const deviceById = new Map<string, DeviceRef>()

  topology.nodes.forEach((node) => {
    nodeNameById.set(node.id, node.name)
  })
  topology.sensors.forEach((sensor) => {
    deviceNodeById.set(sensor.id, sensor.deployedOnNodeId)
    deviceById.set(sensor.id, { id: sensor.id, name: sensor.name, type: 'sensor' })
  })
  topology.actuators.forEach((actuator) => {
    deviceNodeById.set(actuator.id, actuator.deployedOnNodeId)
    deviceById.set(actuator.id, { id: actuator.id, name: actuator.name, type: 'actuator' })
  })
  topology.coordinators.forEach((coordinator) => {
    deviceNodeById.set(coordinator.id, coordinator.deployedOnNodeId)
    deviceById.set(coordinator.id, { id: coordinator.id, name: coordinator.name, type: 'coordinator' })
  })

  return topics.topics.map((topic) => {
    const fromNodes = uniqueStrings((topic.producers ?? []).map((deviceId) => deviceNodeById.get(deviceId)))
      .map((nodeId) => nodeNameById.get(nodeId))
      .filter((nodeName): nodeName is string => Boolean(nodeName))
      .sort((a, b) => a.localeCompare(b))
    const toNodes = uniqueStrings((topic.consumers ?? []).map((deviceId) => deviceNodeById.get(deviceId)))
      .map((nodeId) => nodeNameById.get(nodeId))
      .filter((nodeName): nodeName is string => Boolean(nodeName))
      .sort((a, b) => a.localeCompare(b))

    const producersByNode: NodeDeviceMap = {}
    const consumersByNode = {} as NodeDeviceMap

    const producers = topic.producers ?? []
    const consumers = topic.consumers ?? []

    producers.forEach((deviceId: string) => {
      const nodeId = deviceNodeById.get(deviceId)
      const nodeName = nodeId && nodeNameById.get(nodeId)
      const device = deviceById.get(deviceId)
      if (nodeName && device) {
        if (!producersByNode[nodeName]) producersByNode[nodeName] = []
        producersByNode[nodeName].push(device)
      }
    })

    consumers.forEach((deviceId: string) => {
      const nodeId = deviceNodeById.get(deviceId)
      const nodeName = nodeId && nodeNameById.get(nodeId)
      const device = deviceById.get(deviceId)
      if (nodeName && device) {
        if (!consumersByNode[nodeName]) consumersByNode[nodeName] = []
        consumersByNode[nodeName].push(device)
      }
    })

    return {
      id: topic.id,
      name: topic.name,
      path: topic.path,
      qos: topic.qos,
      fromNodes,
      toNodes,
      producersByNode,
      consumersByNode,
    }
  })
}

export default function TopologyDiagram({
  topology,
  topics,
  selectedNodeId,
  onSelectNode,
}: {
  topology: DashboardTopology
  topics: DashboardTopics
  selectedNodeId: string | null
  onSelectNode: (nodeId: string) => void
}) {
  const topologyDiagram = useMemo(() => buildTopologyDiagram(topology, topics), [topology, topics])
  const topicRoutes = useMemo(() => buildTopicRouteSummaries(topology, topics), [topology, topics])
  const [selectedTopicId, setSelectedTopicId] = useState<string | null>(null)

  return (
    <section className="topology-diagram" aria-label="Node connection diagram">
      <div className="topology-diagram__legend" aria-hidden="true">
        {( ['source', 'logic', 'sink'] as const).map((lane) => (
          <div className={`topology-diagram__legend-item topology-diagram__legend-item--${lane}`} key={lane}>
            <span>{laneLabel(lane)}</span>
          </div>
        ))}
      </div>

      <div className="topology-diagram__canvas" style={{ minHeight: `${topologyDiagram.canvasHeight}px` }}>
        <div className="topology-diagram__lane-band topology-diagram__lane-band--source" />
        <div className="topology-diagram__lane-band topology-diagram__lane-band--logic" />
        <div className="topology-diagram__lane-band topology-diagram__lane-band--sink" />

        <svg aria-hidden="true" className="topology-diagram__links" viewBox={`0 0 1000 ${topologyDiagram.canvasHeight}`} preserveAspectRatio="none">
          <defs>
            <marker id="arrowhead" markerWidth="10" markerHeight="10" refX="8" refY="5" orient="auto">
              <path d="M0,0 L0,10 L10,5 z" />
            </marker>
          </defs>

          {topologyDiagram.edges.map((edge) => {
            const from = topologyDiagram.nodes.find((n) => n.node.id === edge.fromNodeId)
            const to = topologyDiagram.nodes.find((n) => n.node.id === edge.toNodeId)
            if (!from || !to) return null
            const isHighlighted = !selectedNodeId || edge.fromNodeId === selectedNodeId || edge.toNodeId === selectedNodeId
            const width = 1000
            const height = topologyDiagram.canvasHeight
            const fromX = (from.position.x / 100) * width
            const fromY = (from.position.y / 100) * height
            const toX = (to.position.x / 100) * width
            const toY = (to.position.y / 100) * height
            const path = routeEdgePath(fromX, fromY, toX, toY)
            const labelX = (fromX + toX) / 2
            const labelY = (fromY + toY) / 2 - 18

            return (
              <g key={edge.id}>
                <path
                  className={`topology-link ${selectedNodeId ? (isHighlighted ? 'topology-link--active' : 'topology-link--muted') : ''}`}
                  d={path}
                  markerEnd="url(#arrowhead)"
                />
                <text className="topology-link__label" x={labelX} y={labelY}>
                  {edgeLabel(edge.topics)}
                </text>
              </g>
            )
          })}
        </svg>

        {topologyDiagram.nodes.map((entry) => (
          <button
            key={entry.node.id}
            type="button"
            className={`topology-node ${selectedNodeId === entry.node.id ? 'topology-node--selected' : ''} ${selectedNodeId && selectedNodeId !== entry.node.id ? 'topology-node--muted' : ''}`}
            style={{ left: `${entry.position.x}%`, top: `${entry.position.y}%` }}
            onClick={() => onSelectNode(entry.node.id)}
            aria-pressed={selectedNodeId === entry.node.id}
            aria-label={`Select node ${entry.node.name}`}
          >
            <p className="node-kind">Raspberry Pi</p>
            <h3>{entry.node.name}</h3>
            <div className="topology-node__roles">
              {entry.sensors.length > 0 && <span className="topology-node__role topology-node__role--source">Sensor</span>}
              {entry.coordinators.length > 0 && <span className="topology-node__role topology-node__role--logic">Logic</span>}
              {entry.actuators.length > 0 && <span className="topology-node__role topology-node__role--sink">Actuator</span>}
            </div>
            <div className="topology-node__meta">
              <span>{entry.inboundTopics.length} incoming topics</span>
              <span>{entry.outboundTopics.length} outgoing topics</span>
            </div>
            <div className="topology-node__devices">
              <span>{entry.sensors.length} sensors</span>
              <span>{entry.actuators.length} actuators</span>
              <span>{entry.coordinators.length} coordinators</span>
            </div>
          </button>
        ))}
      </div>

      <div className="topology-diagram__summary">
        <div className="topology-diagram__topics-palette">
          <strong>Topics</strong>
          <div className="topology-diagram__topics-list">
            {topicRoutes.map((topic) => (
              <article
                className={`topology-topic-flow ${selectedTopicId === topic.id ? 'topology-topic-flow--selected' : ''}`}
                key={topic.id}
                title={topic.path}
                onClick={() => setSelectedTopicId((cur) => (cur === topic.id ? null : topic.id))}
                role="button"
                tabIndex={0}
                onKeyDown={(e) => {
                  if (e.key === 'Enter' || e.key === ' ') {
                    e.preventDefault()
                    setSelectedTopicId((cur) => (cur === topic.id ? null : topic.id))
                  }
                }}
              >
                <div className="topology-topic-flow__head">
                  <strong>{topic.name}</strong>
                  <span className="chip chip--compact">qos {topic.qos}</span>
                </div>
                <p className="topology-topic-flow__path">{topic.path}</p>
                <p className="topology-topic-flow__route">
                  from {topic.fromNodes.length > 0 ? topic.fromNodes.join(', ') : 'none'} to {topic.toNodes.length > 0 ? topic.toNodes.join(', ') : 'none'}
                </p>

                {selectedTopicId === topic.id && Object.keys(topic.producersByNode).length > 0 && (
                  <div className="topology-topic-devices__section">
                    <h5>Producing from</h5>
                    {Object.entries(topic.producersByNode).map(([nodeName, devices]) => (
                      <div key={`prod-${nodeName}`} className="topology-topic-devices__node">
                        <p className="topology-topic-devices__node-name">{nodeName}</p>
                        <ul className="topology-topic-devices__list">
                          {devices.map((device) => (
                            <li key={device.id}>
                              <span className="topology-topic-devices__device-name">{device.name}</span>
                              <span className={`chip chip--compact topology-device-type topology-device-type--${device.type}`}>
                                {device.type}
                              </span>
                            </li>
                          ))}
                        </ul>
                      </div>
                    ))}
                  </div>
                )}

                {selectedTopicId === topic.id && Object.keys(topic.consumersByNode).length > 0 && (
                  <div className="topology-topic-devices__section">
                    <h5>Consuming to</h5>
                    {Object.entries(topic.consumersByNode).map(([nodeName, devices]) => (
                      <div key={`cons-${nodeName}`} className="topology-topic-devices__node">
                        <p className="topology-topic-devices__node-name">{nodeName}</p>
                        <ul className="topology-topic-devices__list">
                          {devices.map((device) => (
                            <li key={device.id}>
                              <span className="topology-topic-devices__device-name">{device.name}</span>
                              <span className={`chip chip--compact topology-device-type topology-device-type--${device.type}`}>
                                {device.type}
                              </span>
                            </li>
                          ))}
                        </ul>
                      </div>
                    ))}
                  </div>
                )}
              </article>
            ))}
          </div>
        </div>
      </div>
    </section>
  )
}



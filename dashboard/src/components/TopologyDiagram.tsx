import { useMemo } from 'react'
import type { DashboardTopology, DashboardTopics } from '../types'
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

function classifyNodeLane(node: {
  sensors: DashboardTopology['sensors']
  actuators: DashboardTopology['actuators']
  coordinators: DashboardTopology['coordinators']
}): DiagramLane {
  if (node.coordinators.length > 0 || (node.sensors.length > 0 && node.actuators.length > 0)) {
    return 'logic'
  }
  if (node.actuators.length > 0) return 'sink'
  return 'source'
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

  // compute x positions adaptively: if a lane is empty, move others slightly inward
  const xByLane: Record<DiagramLane, number> = { source: 18, logic: 50, sink: 82 }
  const occupied = { source: laneBuckets.source.length > 0, logic: laneBuckets.logic.length > 0, sink: laneBuckets.sink.length > 0 }
  if (!occupied.logic) {
    // shift source/sink inward
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

export default function TopologyDiagram({ topology, topics }: { topology: DashboardTopology; topics: DashboardTopics }) {
  const topologyDiagram = useMemo(() => buildTopologyDiagram(topology, topics), [topology, topics])

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
            <marker id="arrowhead" markerWidth="14" markerHeight="14" refX="11" refY="6" orient="auto">
              <path d="M0,0 L0,12 L14,6 z" />
            </marker>
          </defs>

          {topologyDiagram.edges.map((edge) => {
            const from = topologyDiagram.nodes.find((n) => n.node.id === edge.fromNodeId)
            const to = topologyDiagram.nodes.find((n) => n.node.id === edge.toNodeId)
            if (!from || !to) return null
            const width = 1000
            const height = topologyDiagram.canvasHeight
            const fromX = (from.position.x / 100) * width
            const fromY = (from.position.y / 100) * height
            const toX = (to.position.x / 100) * width
            const toY = (to.position.y / 100) * height
            const bend = Math.max(Math.abs(toX - fromX) * 0.35, 100)
            const path = `M ${fromX} ${fromY} C ${fromX + bend} ${fromY}, ${toX - bend} ${toY}, ${toX} ${toY}`
            const labelX = (fromX + toX) / 2
            const labelY = (fromY + toY) / 2 - 18

            return (
              <g key={edge.id}>
                <path className="topology-link" d={path} markerEnd="url(#arrowhead)" />
                <text className="topology-link__label" x={labelX} y={labelY}>
                  {edgeLabel(edge.topics)}
                </text>
              </g>
            )
          })}
        </svg>

        {topologyDiagram.nodes.map((entry) => (
          <article key={entry.node.id} className="topology-node" style={{ left: `${entry.position.x}%`, top: `${entry.position.y}%` }}>
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
          </article>
        ))}
      </div>

      {topologyDiagram.edges.length > 0 ? (
        <div className="topology-diagram__summary">
          {topologyDiagram.edges.map((edge) => {
            const fromNode = topologyDiagram.nodes.find((entry) => entry.node.id === edge.fromNodeId)
            const toNode = topologyDiagram.nodes.find((entry) => entry.node.id === edge.toNodeId)
            return (
              <div className="topology-diagram__summary-item" key={edge.id} title={edge.topics.join(', ')}>
                <strong>
                  {fromNode?.node.name} → {toNode?.node.name}
                </strong>
                <span>{edgeLabel(edge.topics)}</span>
              </div>
            )
          })}
        </div>
      ) : (
        <p className="empty-state topology-diagram__empty">No inter-node topic connections were found.</p>
      )}
    </section>
  )
}



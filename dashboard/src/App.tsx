import { useEffect, useMemo, useState } from 'react'
import mqtt, { type IClientOptions, type ISubscriptionGrant, type MqttClient } from 'mqtt'
import './App.css'
import TopologyDiagram from './components/TopologyDiagram'
import type {
  DashboardConnectionStatus,
  DashboardMessageEvent,
  DashboardTopology,
  DashboardTopics,
} from './types'

const emptyTopology: DashboardTopology = {
  systemName: 'Waiting for generated model',
  broker: {
    host: '',
    port: 0,
  },
  nodes: [],
  sensors: [],
  actuators: [],
  coordinators: [],
}

const emptyTopics: DashboardTopics = {
  systemName: 'Waiting for generated model',
  broker: emptyTopology.broker,
  topics: [],
}

const DEFAULT_MOSQUITTO_WS_PORT = 9001

async function loadJsonWithStatus<T>(url: string, fallback: T): Promise<{ data: T; missing: boolean }> {
  try {
    const response = await fetch(url)

    if (!response.ok) {
      return { data: fallback, missing: true }
    }

    return { data: (await response.json()) as T, missing: false }
  } catch {
    return { data: fallback, missing: true }
  }
}

function stringifyPayload(payload: unknown): string {
  if (typeof payload === 'string') {
    return payload
  }

  return JSON.stringify(payload)
}

function normalizePayload(rawPayload: string): unknown {
  try {
    return JSON.parse(rawPayload)
  } catch {
    return rawPayload
  }
}

function formatTime(value: string): string {
  return new Intl.DateTimeFormat('en-GB', {
    hour: '2-digit',
    minute: '2-digit',
    second: '2-digit',
  }).format(new Date(value))
}

function connectionLabel(status: DashboardConnectionStatus): string {
  switch (status) {
    case 'connected':
      return 'Connected'
    case 'connecting':
      return 'Connecting'
    case 'error':
      return 'Connection error'
    case 'disconnected':
      return 'Disconnected'
    default:
      return 'Idle'
  }
}

function topicGrantLabel(grant?: ISubscriptionGrant): string {
  if (!grant) {
    return 'pending'
  }

  if (grant.qos === 0 || grant.qos === 1 || grant.qos === 2) {
    return `qos ${grant.qos}`
  }

  return 'subscribed'
}

type DiagramLane = 'source' | 'logic' | 'sink'

type DiagramNode = {
  node: DashboardTopology['nodes'][number]
  sensors: DashboardTopology['sensors']
  actuators: DashboardTopology['actuators']
  coordinators: DashboardTopology['coordinators']
  lane: DiagramLane
  inboundTopics: string[]
  outboundTopics: string[]
  position: {
    x: number
    y: number
  }
}

type DiagramEdge = {
  id: string
  fromNodeId: string
  toNodeId: string
  topics: string[]
}

function uniqueStrings(values: Array<string | undefined | null>): string[] {
  return Array.from(new Set(values.filter((value): value is string => Boolean(value))))
}

function clamp(value: number, min: number, max: number): number {
  return Math.min(max, Math.max(min, value))
}

// lane labeling and diagram helpers are provided by the extracted TopologyDiagram component

function classifyNodeLane(node: {
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

function layoutLanePosition(lane: DiagramLane, index: number, total: number): { x: number; y: number } {
  const xByLane: Record<DiagramLane, number> = {
    source: 12,
    logic: 50,
    sink: 88,
  }

  if (total <= 1) {
    return { x: xByLane[lane], y: 50 }
  }

  // Distribute nodes evenly within a vertical band (16%..84%) so they don't cluster
  const bandTop = 16
  const bandBottom = 84
  const bandHeight = bandBottom - bandTop
  const step = bandHeight / (total + 1)
  const yBase = bandTop + step * (index + 1)

  // small alternating nudge to break perfect alignment
  const nudge = (index % 2 === 0 ? -3 : 3)

  return {
    x: xByLane[lane],
    y: clamp(yBase + nudge, bandTop, bandBottom),
  }
}

function buildTopologyDiagram(topology: DashboardTopology, topics: DashboardTopics): {
  nodes: DiagramNode[]
  edges: DiagramEdge[]
  canvasHeight: number
} {
  const deviceNodeById = new Map<string, string>()

  topology.sensors.forEach((sensor) => {
    deviceNodeById.set(sensor.id, sensor.deployedOnNodeId)
  })
  topology.actuators.forEach((actuator) => {
    deviceNodeById.set(actuator.id, actuator.deployedOnNodeId)
  })
  topology.coordinators.forEach((coordinator) => {
    deviceNodeById.set(coordinator.id, coordinator.deployedOnNodeId)
  })

  const edgeMap = new Map<string, DiagramEdge>()
  const inboundByNode = new Map<string, Set<string>>()
  const outboundByNode = new Map<string, Set<string>>()

  topology.nodes.forEach((node) => {
    inboundByNode.set(node.id, new Set<string>())
    outboundByNode.set(node.id, new Set<string>())
  })

  topics.topics.forEach((topic) => {
    const producerNodeIds = uniqueStrings(
      (topic.producers ?? []).map((deviceId) => deviceNodeById.get(deviceId)),
    )
    const consumerNodeIds = uniqueStrings(
      (topic.consumers ?? []).map((deviceId) => deviceNodeById.get(deviceId)),
    )

    producerNodeIds.forEach((fromNodeId) => {
      outboundByNode.get(fromNodeId)?.add(topic.path)

      consumerNodeIds.forEach((toNodeId) => {
        inboundByNode.get(toNodeId)?.add(topic.path)

        if (fromNodeId === toNodeId) {
          return
        }

        const key = `${fromNodeId}→${toNodeId}`
        const existing = edgeMap.get(key)

        if (existing) {
          existing.topics.push(topic.path)
          return
        }

        edgeMap.set(key, {
          id: key,
          fromNodeId,
          toNodeId,
          topics: [topic.path],
        })
      })
    })
  })

  const nodes = topology.nodes.map((node) => {
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
    }
  }) as Array<Omit<DiagramNode, 'position'>>

  const laneBuckets: Record<DiagramLane, Array<Omit<DiagramNode, 'position'>>> = {
    source: [],
    logic: [],
    sink: [],
  }

  nodes.forEach((entry) => {
    laneBuckets[entry.lane].push(entry)
  })

  const orderedNodes: DiagramNode[] = (['source', 'logic', 'sink'] as const).flatMap((lane) =>
    laneBuckets[lane].map((entry, index) => ({
      ...entry,
      position: layoutLanePosition(lane, index, laneBuckets[lane].length),
    })),
  )

  const maxInLane = Math.max(laneBuckets.source.length, laneBuckets.logic.length, laneBuckets.sink.length)
  // base 480px, add more vertical spacing per node in the tallest lane to avoid overlap
  const canvasHeight = Math.max(480, 180 + maxInLane * 160)

  return {
    nodes: orderedNodes,
    edges: Array.from(edgeMap.values()).sort((left, right) => left.id.localeCompare(right.id)),
    canvasHeight,
  }
}

function App() {
  const [topology, setTopology] = useState<DashboardTopology>(emptyTopology)
  const [topics, setTopics] = useState<DashboardTopics>(emptyTopics)
  const [status, setStatus] = useState<DashboardConnectionStatus>('idle')
  const [statusDetail, setStatusDetail] = useState('Loading dashboard model...')
  const [missingFiles, setMissingFiles] = useState<string[]>([])
  const [events, setEvents] = useState<DashboardMessageEvent[]>([])

  const topologyDiagram = useMemo(() => buildTopologyDiagram(topology, topics), [topology, topics])

  useEffect(() => {
    let active = true

    void (async () => {
      const [topologyResult, topicsResult] = await Promise.all([
        loadJsonWithStatus('/model/topology.json', emptyTopology),
        loadJsonWithStatus('/model/topics.json', emptyTopics),
      ])

      if (!active) {
        return
      }

      const loadedTopology = topologyResult.data
      const loadedTopics = topicsResult.data

      setTopology(loadedTopology)
      setTopics(loadedTopics)

      const missingFiles = [
        topologyResult.missing ? 'topology.json' : null,
        topicsResult.missing ? 'topics.json' : null,
      ].filter((file): file is string => file !== null)

      setMissingFiles(missingFiles)

      setStatusDetail(
        missingFiles.length > 0
          ? `Missing generated file(s): ${missingFiles.join(', ')}. The dashboard is showing an empty state until the DSL generator writes them.`
          : `Loaded ${loadedTopology.nodes.length} nodes and ${loadedTopics.topics.length} topics`,
      )
    })()

    return () => {
      active = false
    }
  }, [])

  useEffect(() => {
    if (!topics.topics.length) {
      return undefined
    }

    const protocol = topology.broker.wsProtocol ?? 'ws'
    const port = topics.broker.wsPort ?? topology.broker.wsPort ?? DEFAULT_MOSQUITTO_WS_PORT
    const url = `${protocol}://${topology.broker.host}:${port}/mqtt`
    const options: IClientOptions = {
      clean: true,
      reconnectPeriod: 4000,
      keepalive: 30,
      connectTimeout: 5000,
    }

    let client: MqttClient | undefined

    try {
      client = mqtt.connect(url, options)
    } catch (error) {
      const message = error instanceof Error ? error.message : 'Unable to connect'
      queueMicrotask(() => {
        setStatus('error')
        setStatusDetail(message)
      })
      return undefined
    }

    client.on('connect', () => {
      const topicPaths = Array.from(
        new Set(topics.topics.map((topic) => topic.path).filter(Boolean)),
      )

      client?.subscribe(topicPaths, { qos: 1 }, (error, grants) => {
        if (error) {
          setStatus('error')
          setStatusDetail(error.message)
          return
        }

        const grantLabel = grants?.map((grant) => topicGrantLabel(grant)).join(', ')

        setStatus('connected')
        setStatusDetail(
          grantLabel
            ? `Subscribed to ${topicPaths.length} topics (${grantLabel})`
            : `Subscribed to ${topicPaths.length} topics`,
        )
      })
    })

    client.on('message', (topic, rawPayload, packet) => {
      const payloadText = rawPayload.toString('utf8')
      const nextEvent: DashboardMessageEvent = {
        topic,
        payloadText,
        parsedPayload: normalizePayload(payloadText),
        timestamp: new Date().toISOString(),
        qos: packet.qos,
        retained: packet.retain,
      }

      setEvents((currentEvents) => [nextEvent, ...currentEvents].slice(0, 40))
    })

    client.on('reconnect', () => {
      setStatus('connecting')
      setStatusDetail(`Reconnecting to ${url}`)
    })

    client.on('close', () => {
      setStatus('disconnected')
      setStatusDetail('MQTT connection closed')
    })

    client.on('error', (error) => {
      setStatus('error')
      setStatusDetail(error.message)
    })

    return () => {
      client?.end(true)
    }
  }, [topics.topics, topics.broker.wsPort, topology.broker.host, topology.broker.wsPort, topology.broker.wsProtocol])

  const statusClassName = `status-pill status-pill--${status}`
  const hasGeneratedModel = topology.nodes.length > 0 || topics.topics.length > 0
  const mqttWsPort = topics.broker.wsPort ?? topology.broker.wsPort ?? DEFAULT_MOSQUITTO_WS_PORT
  const mqttUrl = `${topology.broker.wsProtocol ?? 'ws'}://${topology.broker.host}:${mqttWsPort}/mqtt`
  const panelNote = missingFiles.length > 0
    ? 'Generated JSON is missing or incomplete. Once the DSL generator writes the model, the dashboard will load it automatically.'
    : !hasGeneratedModel
      ? 'Waiting for generated model files...'
      : status === 'idle'
        ? `Connecting to ${mqttUrl}`
        : statusDetail

  return (
    <div className="app-shell">
      <header className="hero-panel">
        <div>
          <p className="eyebrow">Elixir of Things Live Dashboard</p>
          <h1>{topology.systemName}</h1>
          <p className="hero-copy">
            Visualize nodes, sensors, actuators, and live MQTT messages in one place.
          </p>
        </div>

        <div className="hero-meta">
          <div className={statusClassName}>{connectionLabel(status)}</div>
          <div className="meta-card">
            <span>Broker</span>
            <strong>
              {topology.broker.host}:{topics.broker.port}
            </strong>
          </div>
          <div className="meta-card">
            <span>Topics</span>
            <strong>{topics.topics.length}</strong>
          </div>
          <div className="meta-card">
            <span>Messages</span>
            <strong>{events.length}</strong>
          </div>
        </div>
      </header>

      <main className="dashboard-grid">
        <section className="panel topology-panel">
          <div className="panel-heading">
            <div>
              <p className="panel-kicker">Topology</p>
              <h2>Node connections</h2>
            </div>
            <p className="panel-note">{panelNote}</p>
          </div>

          <TopologyDiagram topology={topology} topics={topics} />

          <div className="node-grid">
            {topologyDiagram.nodes.map((entry) => (
              <article key={entry.node.id} className="node-card">
                <div className="node-card__title">
                  <div>
                    <p className="node-kind">Raspberry Pi</p>
                    <h3>{entry.node.name}</h3>
                  </div>
                  <span className="node-count">
                    {entry.sensors.length + entry.actuators.length + entry.coordinators.length} items
                  </span>
                </div>

                <div className="node-card__connections">
                  <span>{entry.inboundTopics.length} inbound</span>
                  <span>{entry.outboundTopics.length} outbound</span>
                </div>

                <div className="device-group">
                  <h4>Sensors</h4>
                  {entry.sensors.length > 0 ? (
                    entry.sensors.map((sensor) => (
                      <div className="device-row" key={sensor.id}>
                        <div>
                          <strong>{sensor.name}</strong>
                          <span>
                            {sensor.type} · GPIO {sensor.gpioPin} · {sensor.sampleRateMs} ms
                          </span>
                        </div>
                        <div className="chip">sensor</div>
                      </div>
                    ))
                  ) : (
                    <p className="empty-state">No sensors assigned.</p>
                  )}
                </div>

                <div className="device-group">
                  <h4>Actuators</h4>
                  {entry.actuators.length > 0 ? (
                    entry.actuators.map((actuator) => (
                      <div className="device-row" key={actuator.id}>
                        <div>
                          <strong>{actuator.name}</strong>
                          <span>
                            {actuator.type} · GPIO {actuator.gpioPin}
                          </span>
                        </div>
                        <div className="chip chip--muted">actuator</div>
                      </div>
                    ))
                  ) : (
                    <p className="empty-state">No actuators assigned.</p>
                  )}
                </div>

                <div className="device-group">
                  <h4>Coordinators</h4>
                  {entry.coordinators.length > 0 ? (
                    entry.coordinators.map((coordinator) => (
                      <div className="device-row" key={coordinator.id}>
                        <div>
                          <strong>{coordinator.name}</strong>
                          <span>{coordinator.subscribeToTopicIds?.length ?? 0} subscriptions</span>
                        </div>
                        <div className="chip chip--accent">logic</div>
                      </div>
                    ))
                  ) : (
                    <p className="empty-state">No coordinators assigned.</p>
                  )}
                </div>
              </article>
            ))}
          </div>
        </section>

        <aside className="side-column">
          <section className="panel topic-panel">
            <div className="panel-heading">
              <div>
                <p className="panel-kicker">MQTT</p>
                <h2>Topics</h2>
              </div>
            </div>

            <div className="topic-list">
              {topics.topics.map((topic) => (
                <article className="topic-row" key={topic.id}>
                  <div>
                    <strong>{topic.name}</strong>
                    <span>{topic.path}</span>
                  </div>
                  <div className="topic-row__meta">
                    <span className="chip chip--compact">qos {topic.qos}</span>
                    <span className="topic-role">
                      {topic.producers?.length ?? 0} producers · {topic.consumers?.length ?? 0}{' '}
                      consumers
                    </span>
                  </div>
                </article>
              ))}
            </div>
          </section>

          <section className="panel stream-panel">
            <div className="panel-heading">
              <div>
                <p className="panel-kicker">Live feed</p>
                <h2>Incoming messages</h2>
              </div>
            </div>

            <div className="stream-list">
              {events.length > 0 ? (
                events.map((event) => (
                  <article className="stream-item" key={`${event.topic}-${event.timestamp}`}>
                    <div className="stream-item__head">
                      <strong>{event.topic}</strong>
                      <span>
                        {formatTime(event.timestamp)} · qos {event.qos}
                        {event.retained ? ' · retained' : ''}
                      </span>
                    </div>
                    <code>{stringifyPayload(event.parsedPayload)}</code>
                  </article>
                ))
              ) : (
                <p className="empty-state">
                  Waiting for MQTT messages. Once the broker publishes, they will appear here.
                </p>
              )}
            </div>
          </section>
        </aside>
      </main>
    </div>
  )
}

export default App

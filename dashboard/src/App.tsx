import { useMemo, useState } from 'react'
import './App.css'
import DashboardHero from './components/DashboardHero'
import MessageFeedPanel from './components/MessageFeedPanel'
import TopologySelectionPanel from './components/TopologySelectionPanel'
import TopologySection from './components/TopologySection'
import { useDashboardModel } from './hooks/useDashboardModel'
import { useMqttFeed } from './hooks/useMqttFeed'
import { resolveMqttWebSocketUrl } from './utils/dashboard'
import { buildNodeSummaries } from './utils/topology'

function App() {
  const { topology, topics, missingFiles, statusDetail: modelStatusDetail } = useDashboardModel()
  const { status, statusDetail: mqttStatusDetail, events, activeActuatorIds } = useMqttFeed(topology, topics)
  const [selectedNodeId, setSelectedNodeId] = useState<string | null>(null)

  const hasGeneratedModel = topology.nodes.length > 0 || topics.topics.length > 0
  const mqttUrl = useMemo(() => resolveMqttWebSocketUrl(topology, topics), [topology, topics])
  const nodeSummaries = useMemo(() => buildNodeSummaries(topology, topics), [topology, topics])
  const selectedNode = useMemo(
    () => nodeSummaries.find((entry) => entry.node.id === selectedNodeId) ?? null,
    [nodeSummaries, selectedNodeId],
  )
  const panelNote = missingFiles.length > 0
    ? 'Generated JSON is missing or incomplete. Once the DSL generator writes the model, the dashboard will load it automatically.'
    : !hasGeneratedModel
      ? modelStatusDetail
      : status === 'idle'
        ? `Connecting to ${mqttUrl}`
        : mqttStatusDetail

  return (
    <div className="app-shell">
      <DashboardHero
        systemName={topology.systemName}
        status={status}
        brokerHost={topology.broker.host}
        brokerPort={topics.broker.port}
        topicCount={topics.topics.length}
        messageCount={events.length}
      />

      <main className="dashboard-grid">
        <TopologySection
          topology={topology}
          topics={topics}
          activeActuatorIds={activeActuatorIds}
          panelNote={panelNote}
          selectedNodeId={selectedNodeId}
          onSelectNode={setSelectedNodeId}
        />

        <aside className="side-column">
          <TopologySelectionPanel
            selectedNode={selectedNode}
            activeActuatorIds={activeActuatorIds}
            onClearSelection={() => setSelectedNodeId(null)}
          />
          <MessageFeedPanel events={events} />
        </aside>
      </main>
    </div>
  )
}

export default App

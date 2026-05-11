import type { DashboardTopology, DashboardTopics } from '../types'
import TopologyDiagram from './TopologyDiagram'

interface TopologySectionProps {
  topology: DashboardTopology
  topics: DashboardTopics
  panelNote: string
  selectedNodeId: string | null
  onSelectNode: (nodeId: string) => void
}

export default function TopologySection({ topology, topics, panelNote, selectedNodeId, onSelectNode }: TopologySectionProps) {
  return (
    <section className="panel topology-panel">
      <div className="panel-heading">
        <div>
          <p className="panel-kicker">Topology</p>
          <h2>Node connections</h2>
        </div>
        <p className="panel-note">{panelNote}</p>
      </div>

      <div className="topology-panel__layout">
        <div className="topology-panel__main">
          <TopologyDiagram
            topology={topology}
            topics={topics}
            selectedNodeId={selectedNodeId}
            onSelectNode={onSelectNode}
          />
        </div>

      </div>
    </section>
  )
}


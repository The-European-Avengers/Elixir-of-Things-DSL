import type { NodeSummary } from '../utils/topology'

interface TopologySelectionPanelProps {
  selectedNode: NodeSummary | null
  onClearSelection: () => void
}

function renderTopicConnections(
  title: string,
  topicsList: NodeSummary['inboundTopicDetails'],
  emptyMessage: string,
  relationLabel: 'from' | 'to',
) {
  return (
    <div className="device-group topology-selection-panel__group">
      <h4>{title}</h4>
      {topicsList.length > 0 ? (
        topicsList.map((topic) => (
          <div className="topic-row topic-row--compact" key={topic.id}>
            <div>
              <strong>{topic.name}</strong>
              <span>{topic.path}</span>
              <span className="topic-row__nodes">
                {topic.connectedNodes.length > 0 ? `${relationLabel} ${topic.connectedNodes.join(', ')}` : 'Local only'}
              </span>
            </div>
            <div className="topic-row__meta">
              <span className="chip chip--compact">qos {topic.qos}</span>
              <span className="topic-role">{topic.connectedNodes.length > 0 ? `${topic.connectedNodes.length} nodes` : 'local'}</span>
            </div>
          </div>
        ))
      ) : (
        <p className="empty-state">{emptyMessage}</p>
      )}
    </div>
  )
}

export default function TopologySelectionPanel({ selectedNode, onClearSelection }: TopologySelectionPanelProps) {
  return (
    <section className="panel topology-selection-panel" aria-label="Selected node details">
      <div className="topology-selection-panel__heading">
        <div>
          <p className="panel-kicker">Selection</p>
          <h3>{selectedNode ? selectedNode.node.name : 'Choose a node to inspect'}</h3>
        </div>
        {selectedNode ? (
          <button type="button" className="chip chip--compact topology-selection-panel__clear" onClick={onClearSelection}>
            Clear
          </button>
        ) : null}
      </div>

      {selectedNode ? (
        <div className="topology-selection-panel__content">
          <div className="topology-selection-panel__summary">
            <div>
              <strong>{selectedNode.inboundTopics.length}</strong>
              <span>incoming topics</span>
            </div>
            <div>
              <strong>{selectedNode.outboundTopics.length}</strong>
              <span>outgoing topics</span>
            </div>
            <div>
              <strong>{selectedNode.sensors.length + selectedNode.actuators.length + selectedNode.coordinators.length}</strong>
              <span>attached elements (sensors, actuators, coordinators)</span>
            </div>
          </div>

          <div className="topology-selection-panel__elements">
            <div className="device-group">
              <h4>Sensors</h4>
              {selectedNode.sensors.length > 0 ? (
                selectedNode.sensors.map((sensor) => (
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
              {selectedNode.actuators.length > 0 ? (
                selectedNode.actuators.map((actuator) => (
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
              {selectedNode.coordinators.length > 0 ? (
                selectedNode.coordinators.map((coordinator) => (
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
          </div>

          <div className="topology-selection-panel__topics">
            {renderTopicConnections(
              'Incoming topics',
              selectedNode.inboundTopicDetails,
              'No topics are delivered to this node.',
              'from',
            )}
            {renderTopicConnections(
              'Outgoing topics',
              selectedNode.outboundTopicDetails,
              'This node does not publish any topics.',
              'to',
            )}
          </div>
        </div>
      ) : (
        <p className="empty-state topology-selection-panel__empty">
          Select a node in the diagram or the cards below to inspect which topics it receives and publishes.
        </p>
      )}
    </section>
  )
}


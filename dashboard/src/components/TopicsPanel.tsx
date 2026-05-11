import type { DashboardTopics } from '../types'

interface TopicsPanelProps {
  topics: DashboardTopics
}

export default function TopicsPanel({ topics }: TopicsPanelProps) {
  return (
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
                {topic.producers?.length ?? 0} producers · {topic.consumers?.length ?? 0} consumers
              </span>
            </div>
          </article>
        ))}
      </div>
    </section>
  )
}


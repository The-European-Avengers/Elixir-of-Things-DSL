import type { DashboardMessageEvent } from '../types'
import { formatTime, stringifyPayload } from '../utils/dashboard'

interface MessageFeedPanelProps {
  events: DashboardMessageEvent[]
}

export default function MessageFeedPanel({ events }: MessageFeedPanelProps) {
  return (
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
  )
}


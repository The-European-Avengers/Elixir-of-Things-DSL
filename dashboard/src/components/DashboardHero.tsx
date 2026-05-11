import type { DashboardConnectionStatus } from '../types'
import { connectionLabel } from '../utils/dashboard'

interface DashboardHeroProps {
  systemName: string
  status: DashboardConnectionStatus
  brokerHost: string
  brokerPort: number
  topicCount: number
  messageCount: number
}

export default function DashboardHero({
  systemName,
  status,
  brokerHost,
  brokerPort,
  topicCount,
  messageCount,
}: DashboardHeroProps) {
  return (
    <header className="hero-panel">
      <div>
        <p className="eyebrow">Elixir of Things Live Dashboard</p>
        <h1>{systemName}</h1>
        <p className="hero-copy">Visualize nodes, sensors, actuators, and live MQTT messages in one place.</p>
      </div>

      <div className="hero-meta">
        <div className={`status-pill status-pill--${status}`}>{connectionLabel(status)}</div>
        <div className="meta-card">
          <span>Broker</span>
          <strong>
            {brokerHost}:{brokerPort}
          </strong>
        </div>
        <div className="meta-card">
          <span>Topics</span>
          <strong>{topicCount}</strong>
        </div>
        <div className="meta-card">
          <span>Messages</span>
          <strong>{messageCount}</strong>
        </div>
      </div>
    </header>
  )
}


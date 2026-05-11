import type {
  DashboardConnectionStatus,
  DashboardTopology,
  DashboardTopics,
  DashboardMessageEvent,
} from '../types'
import type { ISubscriptionGrant } from 'mqtt'

export const DEFAULT_MOSQUITTO_WS_PORT = 9001

export const emptyTopology: DashboardTopology = {
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

export const emptyTopics: DashboardTopics = {
  systemName: 'Waiting for generated model',
  broker: emptyTopology.broker,
  topics: [],
}

export async function loadJsonWithStatus<T>(
  url: string,
  fallback: T,
): Promise<{ data: T; missing: boolean }> {
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

export function stringifyPayload(payload: unknown): string {
  if (typeof payload === 'string') {
    return payload
  }

  const stringified = JSON.stringify(payload)
  return stringified ?? String(payload)
}

export function normalizePayload(rawPayload: string): unknown {
  try {
    return JSON.parse(rawPayload)
  } catch {
    return rawPayload
  }
}

export function formatTime(value: string): string {
  return new Intl.DateTimeFormat('en-GB', {
    hour: '2-digit',
    minute: '2-digit',
    second: '2-digit',
  }).format(new Date(value))
}

export function connectionLabel(status: DashboardConnectionStatus): string {
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

export function topicGrantLabel(grant?: ISubscriptionGrant): string {
  if (!grant) {
    return 'pending'
  }

  if (grant.qos === 0 || grant.qos === 1 || grant.qos === 2) {
    return `qos ${grant.qos}`
  }

  return 'subscribed'
}

export function resolveMqttWebSocketUrl(
  topology: DashboardTopology,
  topics: DashboardTopics,
): string {
  const protocol = topology.broker.wsProtocol ?? 'ws'
  const port = topics.broker.wsPort ?? topology.broker.wsPort ?? DEFAULT_MOSQUITTO_WS_PORT
  return `${protocol}://${topology.broker.host}:${port}/mqtt`
}

export function toDashboardMessageEvent(params: {
  topic: string
  payload: Buffer
  qos?: number
  retained?: boolean
}): DashboardMessageEvent {
  const payloadText = params.payload.toString('utf8')

  return {
    topic: params.topic,
    payloadText,
    parsedPayload: normalizePayload(payloadText),
    timestamp: new Date().toISOString(),
    qos: params.qos,
    retained: params.retained,
  }
}




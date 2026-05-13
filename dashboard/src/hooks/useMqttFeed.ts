import { useEffect, useRef, useState } from 'react'
import mqtt, { type IClientOptions, type MqttClient } from 'mqtt'
import type { DashboardConnectionStatus, DashboardTopology, DashboardTopics, DashboardMessageEvent } from '../types'
import {
  DEFAULT_MOSQUITTO_WS_PORT,
  connectionLabel,
  normalizePayload,
  resolveMqttWebSocketUrl,
  topicGrantLabel,
} from '../utils/dashboard'

function uniqueStrings(values: string[]): string[] {
  return Array.from(new Set(values))
}

export function useMqttFeed(topology: DashboardTopology, topics: DashboardTopics) {
  const [status, setStatus] = useState<DashboardConnectionStatus>('idle')
  const [statusDetail, setStatusDetail] = useState('Waiting for generated model...')
  const [events, setEvents] = useState<DashboardMessageEvent[]>([])
  const [activeActuatorIds, setActiveActuatorIds] = useState<string[]>([])
  const activeTimeoutsRef = useRef<Map<string, number>>(new Map())

  useEffect(() => {
    const activeTimeouts = activeTimeoutsRef.current

    if (!topics.topics.length) {
      activeTimeouts.forEach((timeoutId) => window.clearTimeout(timeoutId))
      activeTimeouts.clear()
      return undefined
    }

    const url = resolveMqttWebSocketUrl(topology, topics)
    const actuatorIds = new Set(topology.actuators.map((actuator) => actuator.id))
    const actuatorIdsByTopicPath = new Map<string, string[]>()

    topics.topics.forEach((topic) => {
      const activeConsumers = uniqueStrings((topic.consumers ?? []).filter((consumerId) => actuatorIds.has(consumerId)))
      if (activeConsumers.length > 0) {
        actuatorIdsByTopicPath.set(topic.path, activeConsumers)
      }
    })

    const options: IClientOptions = {
      clean: true,
      reconnectPeriod: 4000,
      keepalive: 30,
      connectTimeout: 5000,
    }

    let client: MqttClient | undefined

    try {
      client = mqtt.connect(url, options)
      queueMicrotask(() => {
        setStatus('connecting')
        setStatusDetail(`Connecting to ${url}`)
      })
    } catch (error) {
      const message = error instanceof Error ? error.message : 'Unable to connect'
      queueMicrotask(() => {
        setStatus('error')
        setStatusDetail(message)
      })
      return undefined
    }

    client.on('connect', () => {
      const topicPaths = Array.from(new Set(topics.topics.map((topic) => topic.path).filter(Boolean)))

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
      const actuatorsToActivate = actuatorIdsByTopicPath.get(topic) ?? []

      if (actuatorsToActivate.length > 0) {
        setActiveActuatorIds((current) => uniqueStrings([...current, ...actuatorsToActivate]))

        actuatorsToActivate.forEach((actuatorId) => {
          const existingTimeout = activeTimeoutsRef.current.get(actuatorId)
          if (existingTimeout !== undefined) {
            window.clearTimeout(existingTimeout)
          }

          const timeoutId = window.setTimeout(() => {
            activeTimeoutsRef.current.delete(actuatorId)
            setActiveActuatorIds((current) => current.filter((id) => id !== actuatorId))
          }, 5000)

          activeTimeoutsRef.current.set(actuatorId, timeoutId)
        })
      }

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
      activeTimeouts.forEach((timeoutId) => window.clearTimeout(timeoutId))
      activeTimeouts.clear()
    }
  }, [topology, topics])

  return {
    status: topics.topics.length ? status : 'idle',
    statusDetail: topics.topics.length ? statusDetail : 'Waiting for generated model...',
    events,
    activeActuatorIds: topics.topics.length ? activeActuatorIds : [],
    connectionLabel: connectionLabel(status),
    mqttWsPort: topics.broker.wsPort ?? topology.broker.wsPort ?? DEFAULT_MOSQUITTO_WS_PORT,
    mqttUrl: resolveMqttWebSocketUrl(topology, topics),
  }
}




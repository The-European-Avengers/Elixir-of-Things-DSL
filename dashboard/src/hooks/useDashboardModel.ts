import { useEffect, useState } from 'react'
import type { DashboardTopology, DashboardTopics } from '../types'
import { emptyTopology, emptyTopics, loadJsonWithStatus } from '../utils/dashboard'

export function useDashboardModel() {
  const [topology, setTopology] = useState<DashboardTopology>(emptyTopology)
  const [topics, setTopics] = useState<DashboardTopics>(emptyTopics)
  const [missingFiles, setMissingFiles] = useState<string[]>([])
  const [statusDetail, setStatusDetail] = useState('Loading dashboard model...')

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

      const nextMissingFiles = [
        topologyResult.missing ? 'topology.json' : null,
        topicsResult.missing ? 'topics.json' : null,
      ].filter((file): file is string => file !== null)

      setMissingFiles(nextMissingFiles)
      setStatusDetail(
        nextMissingFiles.length > 0
          ? `Missing generated file(s): ${nextMissingFiles.join(', ')}. The dashboard is showing an empty state until the DSL generator writes them.`
          : `Loaded ${loadedTopology.nodes.length} nodes and ${loadedTopics.topics.length} topics`,
      )
    })()

    return () => {
      active = false
    }
  }, [])

  return {
    topology,
    topics,
    missingFiles,
    statusDetail,
  }
}


import React from 'react'
import { Session } from '../Session'
import { Api, Stats } from '../services/Api'
import { formatDistance, parseISO } from 'date-fns'
import { Graph, GraphProps } from '../components/Graph'
import useSWR from 'swr'
import { ErrorDetails } from '../services/Http'
import { useUser } from '../hooks/User'

interface IndexProps {}

interface StatsFetch {
  stats?: Stats
  isLoading: boolean
  error?: object
}

function useStats(session?: Session): StatsFetch {
  const key = session ? '/stats' : null
  const { data, error } = useSWR<Stats, ErrorDetails>(key, (key) =>
    Api.getStats(session!.id)
  )
  return {
    stats: data,
    isLoading: !data && !error,
    error: error,
  }
}

function useGraph(session?: Session) {
  const key = session ? '/graph' : null
  const { data, error } = useSWR<GraphProps, ErrorDetails>(key, (key) =>
    Api.getRandomGraph(session!.id)
  )
  return {
    graph: data,
    isLoading: !data && !error,
    error: error,
  }
}

const Index: React.FC<IndexProps> = () => {
  const { session } = useUser()
  // TODO: Use error/isLoading values
  const { stats } = useStats(session)
  const { graph } = useGraph(session)

  return (
    <div>
      <div className="row d-flex mt-3">
        <div className="col">
          <InfoCard
            title={'Last workout'}
            content={
              stats?.lastWorkout == null
                ? ''
                : `${formatDistance(
                    parseISO(stats.lastWorkout),
                    new Date()
                  )} ago`
            }
          />
        </div>
        <div className="col">
          <InfoCard
            title={'Past 30 days'}
            content={!stats ? '' : `${stats.workoutsLast30Days} workouts`}
          />
        </div>
        <div className="col d-none d-lg-block">
          <InfoCard
            title={'Common exercise'}
            content={!stats ? '' : `${stats.commonExercise}`}
          />
        </div>
      </div>
      <div className="row mt-5">
        <div className="col-sm-4 d-none d-sm-block">
          <div className="card">
            <h2 className="card-header">Info</h2>
            <div className="card-body">
              <div className="card-text">
                Pretend there's some cool info here
                <ul>
                  <li>Item 1</li>
                  <li>Item 2</li>
                  <li>Item 3</li>
                </ul>
              </div>
            </div>
          </div>
        </div>
        <div className="col-sm-8 col-12">
          {graph && <Graph data={graph.data} labels={graph.labels} />}
        </div>
      </div>
    </div>
  )
}

interface InfoCardProps {
  title: String
  content: String
}

const InfoCard: React.FC<InfoCardProps> = (props) => {
  return (
    <div className="card bg-info text-white h-100">
      <div className="card-body">
        <h2 className="card-title">{props.title}</h2>
        <hr />
        <h5 className="card-text">{props.content}</h5>
      </div>
    </div>
  )
}

export default Index

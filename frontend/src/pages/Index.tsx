import React, {useEffect, useState} from "react"
import {Session} from "../Session"
import {Api, Stats} from "../services/Api"
import {formatDistance, parseISO} from 'date-fns'
import {Graph, GraphProps} from "../components/Graph"


interface IndexProps {
    session: Session
}

interface StatsFetch {
    stats: Stats | null,
    loading: boolean,
    error: object | null
}

const Index: React.FC<IndexProps> = ({session}) => {
    const [graph, setGraph] = useState<GraphProps>()
    const [statsFetch, setStatsFetch] = useState<StatsFetch>({
        stats: null,
        loading: false,
        error: null
    })

    useEffect(() => {
        const id = session.id
        if (id == null) {
            return
        }
        setStatsFetch({stats: null, loading: true, error: null})
        Api.getStats(id)
            .then((stats) => {
                setStatsFetch({stats: stats, loading: false, error: null})
            })
            .catch((err) => {
                //TODO: Handle
                setStatsFetch({stats: null, loading: false, error: err})
                console.warn(err)
            })
        Api.getRandomGraph(id)
            .then(graph => setGraph(graph))
            .catch(err => console.warn(err))
    }, [session.id])

    const stats = statsFetch.stats
    // const data: Data[] = [
    //     {
    //         x: ['giraffes', 'orangutans', 'monkeys'],
    //         y: [20, 14, 23],
    //         type: 'bar'
    //     }
    // ];
    // const labels: GraphLabels = {
    //     title: "Workouts per month",
    //     xAxis: "Month",
    //     yAxis: "Workout Number"
    // }
    return (
        <div>
            <div className="row d-flex mt-3">
                <div className="col">
                    <InfoCard title={"Last workout"}
                              content={stats?.lastWorkout == null ? '' : `${formatDistance(parseISO(stats.lastWorkout), new Date())} ago`}/>
                </div>
                <div className="col">
                    <InfoCard title={"Past 30 days"}
                              content={stats == null ? '' : `${stats.workoutsLast30Days} workouts`}/>
                </div>
                <div className="col d-none d-lg-block">
                    <InfoCard title={"Common exercise"} content={stats == null ? '' : `${stats.commonExercise}`}/>
                </div>
            </div>
            <div className="row mt-5">
                <div className="col-4 d-none d-sm-block">
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
                <div className="col-8">
                    {graph &&
                    <Graph data={graph.data} labels={graph.labels}/>
                    }
                </div>
            </div>
        </div>
    )
}

interface InfoCardProps {
    title: String,
    content: String
}

const InfoCard: React.FC<InfoCardProps> = (props) => {
    return (
        <div className="card bg-info text-white h-100">
            <div className="card-body">
                <h2 className="card-title">{props.title}</h2>
                <hr/>
                <h5 className="card-text">{props.content}</h5>
            </div>
        </div>
    )
}

export default Index

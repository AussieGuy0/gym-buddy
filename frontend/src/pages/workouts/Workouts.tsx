import React, {useEffect, useState} from "react"
import {Api, Workout} from "../../services/Api"
import {Session} from "../../Session";
import WorkoutForm from "./WorkoutForm"

export interface SessionProps {
    session: Session
}

interface AllWorkoutsTableProps {
    workouts: Array<Workout>
}
const AllWorkoutsTable: React.FC<AllWorkoutsTableProps> = ({workouts}) => {
    return (
        <table className="table">
            <thead>
            <tr>
                <th>Date</th>
                <th>Title</th>
                <th>Description</th>
            </tr>
            </thead>
            <tbody>
            {workouts.map(workout => {
                return (<tr key={workout.id}>
                    <td>{new Date(workout.date).toDateString()}</td>
                    <td>{workout.title}</td>
                    <td>{workout.description}</td>
                </tr>)
            })}
            </tbody>
        </table>
    )
}

const Workouts: React.FC<SessionProps> = ({session}) => {
    const [workouts, setWorkouts] = useState<Array<Workout>>([])

    useEffect(() => {
        const id = session.id
        if (id == null) {
            return
        }
        Api.getWorkouts(id)
            .then((workouts) => {
                setWorkouts(workouts);
            })
            .catch((err) => {
                console.warn(err)
            })


    }, [session.id])
    return (
        <div>
            <div className="row">
                <div className="col">
                    <WorkoutForm session={session}/>
                </div>
            </div>
            <div className="row">
                <div className="col">
                    <h1>Workouts</h1>
                </div>
            </div>
            <div className="row">
                <div className="col">
                    <h2>All Workouts</h2>
                </div>
            </div>
            <div className="row">
                <div className="col">
                    <AllWorkoutsTable workouts={workouts}/>
                </div>
            </div>
        </div>
    );
}

export default Workouts;

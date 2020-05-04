import React, {useEffect, useState} from "react"
import {Api, Workout} from "../../services/Api"
import {Session} from "../../Session"
import {WorkoutForm} from "./WorkoutForm"
import {Button} from "../../components/buttons/buttons"
import { AllWorkoutsTable } from "./AllWorkoutsTable"

export interface SessionProps {
    session: Session
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
                setWorkouts(workouts)
            })
            .catch((err) => {
                //TODO: Handle
                console.warn(err)
            })


    }, [session.id])

    function workoutAdded(workout: Workout) {
        setWorkouts((prev) => [...prev, workout])
    }

    return (
        <div>
            <div className="row">
                <div className="col">
                    <h1>Workouts</h1>
                </div>
            </div>
            <div className="row">
                <div className="col">
                    <Button label="Add Workout" onClick={() => {}} additionalClass="btn-primary col"/>
                </div>
                <div className="col">
                    <Button label="See Previous Workouts" onClick={() => {}} additionalClass="btn-primary col"/>
                </div>
            </div>
            <div className="row">
                <div className="col">
                    <WorkoutForm session={session} workoutAdded={workoutAdded}/>
                </div>
            </div>
            <div className="row">
                <div className="col">
                    <h2>Past Workouts</h2>
                </div>
            </div>
            <div className="row">
                <div className="col">
                    <AllWorkoutsTable workouts={workouts}/>
                </div>
            </div>
        </div>
    )
}

export default Workouts

import React, { useEffect, useState } from "react"
import { Api, Workout } from "../../services/Api"
import { Session } from "../../Session"
import { WorkoutForm } from "./WorkoutForm"
import { AllWorkoutsTable } from "./AllWorkoutsTable"
import { Card } from "../../components/cards"

export interface SessionProps {
  session: Session
}

const Workouts: React.FC<SessionProps> = ({ session }) => {
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
      <div className="row mb-3">
        <div className="col">
          <Card title="Add Workout">
            <WorkoutForm session={session} workoutAdded={workoutAdded} />
          </Card>
        </div>
      </div>
      <div className="row">
        <div className="col">
          <Card title="Past Workouts">
            <AllWorkoutsTable workouts={workouts} />
          </Card>
        </div>
      </div>
    </div>
  )
}

export default Workouts

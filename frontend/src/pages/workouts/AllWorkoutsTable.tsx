import {Workout} from "../../services/Api"
import React, {useState} from "react"
import {format} from "date-fns"
import {Button} from "../../components/buttons/buttons"

interface AllWorkoutsTableProps {
    workouts: Array<Workout>
}

export const AllWorkoutsTable: React.FC<AllWorkoutsTableProps> = ({workouts}) => {
    return (
        <table className="table">
            <thead>
            <tr>
                <th>Date</th>
                <th>Title</th>
                <th className="d-none d-md-table-cell">Description</th>
                <th>Exercises</th>
            </tr>
            </thead>
            <tbody>
            {workouts.map(workout => (<WorkoutsRow key={workout.id} workout={workout}/>))}
            </tbody>
        </table>
    )
}

interface WorkoutsRowProps {
    workout: Workout
}

const WorkoutsRow: React.FC<WorkoutsRowProps> = ({workout}) => {
    const [opened, setOpened] = useState(false)
    const buttonLabel = opened ? "-" : "+"
    return (
        <>
            <tr>
                <td>{format(new Date(workout.date), "d/M/Y")}</td>
                <td>{workout.title}</td>
                <td className="d-none d-md-table-cell">{workout.description}</td>
                <td>
                    <div className="d-flex">
                        <span>{workout.exercises.length}</span> <Button additionalClass="btn-light ml-auto"
                                                                        label={buttonLabel}
                                                                        onClick={() => setOpened(!opened)}/>
                    </div>
                </td>
            </tr>
            {opened && (
                <tr>
                    <td colSpan={4} className="border-top-0 pt-0">
                        <div>{workout.description}</div>
                        <ul>
                            {workout.exercises.map((exercise, idx) => {
                                return (<li key={idx}>
                                    {exercise.name} {`${exercise.sets}x${exercise.reps} ${exercise.weight}kg`}
                                </li>)
                            })}
                        </ul>
                    </td>
                </tr>
            )}
        </>
    )
}

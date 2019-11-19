import React, {useEffect, useState} from "react"
import {Api, Exercise, Workout} from "../services/Api"
import {Session} from "../Session";

export interface SessionProps {
    session: Session
}

const WorkoutForm: React.FC<SessionProps> = ({session}) => {
    const [title, setTitle] = useState('');
    const [description, setDescription] = useState('');
    const defaultExercise = {id: -1, sets: 3, reps: 12}
    const [workoutExercises, setAllWorkoutExercises] = useState<Array<WorkoutExercise>>([defaultExercise])
    const [allExercises, setAllExercises] = useState<Array<Exercise>>([])


    useEffect(() => {
        Api.getExercises()
            .then((exercises) => {
                setAllExercises(exercises)
            })
    }, [])

    function addExercise(evt: React.MouseEvent) {
        setAllWorkoutExercises(current => current.concat(defaultExercise))
    }

    function updateExercise(index: number, updateWorkoutExercise: WorkoutExercise) {
        setAllWorkoutExercises(currentExercises => {
            const updatedWorkoutExercises = [...currentExercises]
            updatedWorkoutExercises[index] = updateWorkoutExercise
            return updatedWorkoutExercises
        })
    }

    function submitWorkout() {
        const id =  session.id || -1;
        Api.addWorkout(id, title, description, workoutExercises)
            .then(result => {
                console.log(result)
            }).catch(err => {
                console.log(err)
        })
    }

    return (
        <div>
            <h1>Add Workout</h1>
            <form>
                <div className='form-group'>
                    <label htmlFor='title'>Title</label>
                    <input className='form-control' type='text' id='title' value={title} onChange={(evt) => setTitle(evt.target.value)}/>
                </div>
                <div className='form-group'>
                    <label htmlFor='description'>Description</label>
                    <input className='form-control' type='text' value={description} id='description' onChange={(evt) => setDescription(evt.target.value)}/>
                </div>
                <div className='form-group'>
                    <label>Exercises</label>
                    {workoutExercises.map((exercise, index) =>
                        (<ExerciseFormItem key={index} exercises={allExercises} selectedExercise={exercise} updateExercise={(updated) => updateExercise(index, updated) }/>)
                    )}
                    <button type='button' className='btn btn-secondary' onClick={addExercise}>
                        Add Exercise
                    </button>

                </div>
                <button type='button' className='btn btn-primary' onClick={submitWorkout}>
                    Add Workout
                </button>
            </form>
        </div>
    );
}

interface ExerciseFormItemProps {
    exercises: Array<Exercise>
    selectedExercise: WorkoutExercise,
    updateExercise(workoutExercise: WorkoutExercise): void
}

interface WorkoutExercise {
    id: number,
    sets: number,
    reps: number,
    weight?: number
}

const ExerciseFormItem: React.FC<ExerciseFormItemProps> = ({exercises, selectedExercise, updateExercise}) => {
    function updateExerciseValue(key: string, evt: React.ChangeEvent<HTMLInputElement | HTMLSelectElement>): void {
        console.log(evt.target.value)
        console.log(Number(evt.target.value))
        console.log(selectedExercise)
        updateExercise({...selectedExercise, [key]: Number(evt.target.value)})
    }
    return (
        <div className="row">
            <select className="form-control col" defaultValue={selectedExercise.id + ''} onChange={(evt) => updateExerciseValue('id', evt)}>
                {exercises.map(exercise => (
                    <option key={exercise.id} value={exercise.id}>{exercise.name}</option>
                ))}
            </select>
            <input type="number" defaultValue={selectedExercise.sets + ''} onChange={(evt) => updateExerciseValue('sets', evt)}/>
            <input type="number" defaultValue={selectedExercise.reps + ''} onChange={(evt) => updateExerciseValue('reps', evt)}/>
            <input type="number" defaultValue={selectedExercise.weight + ''} onChange={(evt) => updateExerciseValue('weight', evt)}/>
        </div>
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
                </div>
            </div>
        </div>
    );
}

export default Workouts;

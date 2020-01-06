import React, {useEffect, useState} from "react"
import {Api, Exercise} from "../../services/Api"
import {removeElement} from "../../utils/utils"
import {SessionProps} from "./Workouts"

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

    function addExercise() {
        setAllWorkoutExercises(current => current.concat(defaultExercise))
    }

    function updateExercise(idx: number, updateWorkoutExercise: WorkoutExercise) {
        setAllWorkoutExercises(currentExercises => {
            const updatedWorkoutExercises = [...currentExercises]
            updatedWorkoutExercises[idx] = updateWorkoutExercise
            return updatedWorkoutExercises
        })
    }

    function removeExercise(idx: number) {
        setAllWorkoutExercises(currentExercises => {
            return removeElement(currentExercises, idx)
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
            <h2>Add Workout</h2>
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
                    <div className="row">
                        <strong className="col-5">Name</strong>
                        <strong className="col-2 pl-0">Sets</strong>
                        <strong className="col-2 pl-0">Reps</strong>
                        <strong className="col-2 pl-0">Weight (kg)</strong>
                    </div>
                    {workoutExercises.map((exercise, index) =>
                        (<ExerciseFormItem key={index} exercises={allExercises} selectedExercise={exercise} updateExercise={(updated) => updateExercise(index, updated) } removeExercise={() => removeExercise(index)}/>)
                    )}
                    <button type='button' className='btn btn-secondary mt-2' onClick={addExercise}>
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
    removeExercise(): void
}

interface WorkoutExercise {
    id: number,
    sets: number,
    reps: number,
    weight?: number
}

const ExerciseFormItem: React.FC<ExerciseFormItemProps> = ({exercises, selectedExercise, updateExercise, removeExercise}) => {
    function updateExerciseValue(key: keyof WorkoutExercise, evt: React.ChangeEvent<HTMLInputElement | HTMLSelectElement>): void {
        updateExercise({...selectedExercise, [key]: Number(evt.target.value)})
    }
    return (
        <div className="row mb-1">
            <div className="col-5">
                <select className="form-control" value={selectedExercise.id + ''} onChange={(evt) => updateExerciseValue('id', evt)}>
                    {exercises.map(exercise => (
                        <option key={exercise.id} value={exercise.id}>{exercise.name}</option>
                    ))}
                </select>
            </div>
            <input className="col-2 form-control" type="number" min='0' value={selectedExercise.sets + ''} onChange={(evt) => updateExerciseValue('sets', evt)}/>
            <input className="col-2 form-control" type="number" min='0' value={selectedExercise.reps + ''} onChange={(evt) => updateExerciseValue('reps', evt)}/>
            <input className="col-2 form-control" type="number" min='0' value={selectedExercise.weight + ''} onChange={(evt) => updateExerciseValue('weight', evt)}/>
            <div className="col-1">
                <button className="btn btn-danger" type="button" onClick={removeExercise}>X</button>
            </div>
        </div>
    )
}

export default WorkoutForm;
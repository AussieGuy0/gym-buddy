import React, {useEffect, useState} from "react"
import {Api, Exercise, Workout} from "../../services/Api"
import {arrayToMap, removeElement} from "../../utils/utils"
import {Session} from "../../Session"
import {ErrorDetails} from "../../services/Http"

export interface WorkoutFormProps {
    session: Session

    workoutAdded(workout: Workout): void
}

const defaultExercise = {id: -1, sets: 3, reps: 12}

export const WorkoutForm: React.FC<WorkoutFormProps> = ({session, workoutAdded}) => {
    const [title, setTitle] = useState('')
    const [description, setDescription] = useState('')
    const [workoutExercises, setAllWorkoutExercises] = useState<Array<WorkoutExercise>>([])
    const [allExercises, setAllExercises] = useState<Array<Exercise>>([])
    const [loading, setLoading] = useState(false)
    const [error, setError] = useState<ErrorDetails | null>(null)
    const [editingExerciseIndex, setEditingExerciseIndex] = useState<number | null>(null)
    const [editingExercise, setEditingExercise] = useState<WorkoutExercise | null>(null)

    //FIXME: This is going to run on every render?
    const exerciseCache = arrayToMap(allExercises, (exercise) => exercise.id)

    useEffect(() => {
        //TODO: Handle error
        Api.getExercises()
            .then((exercises) => {
                setAllExercises(exercises)
            })
    }, [])

    function clear() {
        setTitle('')
        setDescription('')
        setAllWorkoutExercises([defaultExercise])
    }

    function startAddingExercise() {
        editExercise(-1, defaultExercise)
    }

    function editExercise(idx: number, workoutExercise: WorkoutExercise) {
        setEditingExercise(workoutExercise)
        setEditingExerciseIndex(idx)
    }

    function cancelEditingExercise() {
        setEditingExercise(null)
        setEditingExerciseIndex(null)
    }

    function upsertExercise(idx: number, updatedWorkoutExercise: WorkoutExercise) {
        if (idx === -1) {
            setAllWorkoutExercises(current => current.concat(updatedWorkoutExercise))
        } else {
            setAllWorkoutExercises(currentExercises => {
                const updatedWorkoutExercises = [...currentExercises]
                updatedWorkoutExercises[idx] = updatedWorkoutExercise
                return updatedWorkoutExercises
            })
        }
        cancelEditingExercise()
    }

    function removeExercise(idx: number) {
        setAllWorkoutExercises(currentExercises => {
            return removeElement(currentExercises, idx)
        })
    }

    async function submitWorkout() {
        const id = session.id || -1
        setLoading(true)
        setError(null)
        try {
            const savedWorkout = await Api.addWorkout(id, title, description, workoutExercises)
            workoutAdded(savedWorkout)
            clear()
        } catch (err) {
            setError(err)
        } finally {
            setLoading(false)
        }
    }

    return (
        <div>
            <form>
                <div className='form-group'>
                    <label htmlFor='title'>Title</label>
                    <input className='form-control' type='text' id='title' value={title}
                           onChange={(evt) => setTitle(evt.target.value)}/>
                </div>
                <div className='form-group'>
                    <label htmlFor='description'>Description</label>
                    <input className='form-control' type='text' value={description} id='description'
                           onChange={(evt) => setDescription(evt.target.value)}/>
                </div>
                <div className='form-group'>
                    <h4>Exercises</h4>
                    {workoutExercises.length === 0 && (
                        <span className="text-secondary">No exercises have been added to the workout :( </span>
                    )}
                    {workoutExercises.map((exercise, index) =>
                        (<ExerciseItem key={index} workoutExercise={exercise}
                                       removeExercise={() => removeExercise(index)}
                                       editExercise={() => editExercise(index, exercise)}
                                       exerciseCache={exerciseCache}
                        />)
                    )}

                    {editingExercise && editingExerciseIndex != null &&
                    (<ExerciseFormItem exercises={allExercises}
                                       upsertExercise={(updated) => upsertExercise(editingExerciseIndex, updated)}
                                       cancel={cancelEditingExercise}
                                       initialWorkoutExercise={editingExercise}/>)}
                    {editingExercise == null &&
                    (
                        <button type='button' className='btn btn-secondary mt-2'
                                onClick={startAddingExercise}>
                            + Add Exercise
                        </button>
                    )
                    }
                </div>
                <button type='button' className='btn btn-primary mt-5'
                        disabled={loading || editingExercise != null || workoutExercises.length === 0}
                        onClick={submitWorkout}>
                    Save Workout
                </button>
                {
                    error &&
                    (<span className="ml-2 text-danger">{error.message}</span>)
                }
            </form>
        </div>
    )
}

interface ExerciseFormItemProps {
    exercises: Array<Exercise>
    initialWorkoutExercise: WorkoutExercise,

    upsertExercise(workoutExercise: WorkoutExercise): void,

    cancel(): void
}

interface WorkoutExercise {
    id: number,
    sets: number,
    reps: number,
    weight?: number
}

const ExerciseFormItem: React.FC<ExerciseFormItemProps> = ({exercises, initialWorkoutExercise, upsertExercise, cancel}) => {
    const [workoutExercise, setWorkoutExercise] = useState<WorkoutExercise>({...initialWorkoutExercise})

    function updateExerciseValue(key: keyof WorkoutExercise, evt: React.ChangeEvent<HTMLInputElement | HTMLSelectElement>): void {
        setWorkoutExercise({...workoutExercise, [key]: Number(evt.target.value)})
    }

    function isValid(): boolean {
        return workoutExercise.id >= 0 && workoutExercise.sets >= 0 && workoutExercise.reps >= 0
    }

    const exerciseId = workoutExercise.id === -1 ? '' : workoutExercise.id
    return (
        <>
            <div className="row mb-1">
                <div className="col-4">
                    <strong>Name</strong>
                </div>
                <div className="col-8">
                    <select className="form-control" value={exerciseId}
                            onChange={(evt) => updateExerciseValue('id', evt)}>
                        <option value="" disabled hidden>Select...</option>
                        {exercises.map(exercise => (
                            <option key={exercise.id} value={exercise.id}>{exercise.name}</option>
                        ))}
                    </select>
                </div>
            </div>
            <div className="row mb-1">
                <div className="col-4">
                    <strong>Sets x Reps</strong>
                </div>
                <div className="col-4">
                    <input className="form-control" type="number" min='0' value={workoutExercise.sets + ''}
                           onChange={(evt) => updateExerciseValue('sets', evt)}/>
                </div>
                <div className="col-4">
                    <input className="form-control" type="number" min='0' value={workoutExercise.reps + ''}
                           onChange={(evt) => updateExerciseValue('reps', evt)}/>
                </div>
            </div>
            <div className="row">
                <div className="col-4">
                    <strong>Weight</strong>
                </div>
                <div className="col-8">
                    <input className="form-control" type="number" min='0' value={workoutExercise.weight + ''}
                           onChange={(evt) => updateExerciseValue('weight', evt)}/>
                </div>
            </div>
            <div className="row">
                <div className="col">
                    <button className="btn btn-success mr-2" type="button"
                            disabled={!isValid()}
                            onClick={() => upsertExercise(workoutExercise)}>Add
                    </button>
                    <button className="btn btn-danger" type="button" onClick={cancel}>Cancel</button>
                </div>
            </div>
        </>
    )
}

interface ExerciseItemProps {
    workoutExercise: WorkoutExercise
    exerciseCache: Map<number, Exercise>

    removeExercise(): void

    editExercise(workoutExercise: WorkoutExercise): void
}

const ExerciseItem: React.FC<ExerciseItemProps> = ({workoutExercise, removeExercise, editExercise, exerciseCache}) => {
    return (
        <div className="row mb-1">
            <div className="col-5">
                {exerciseCache.get(workoutExercise.id)?.name}
            </div>
            <div className="col-3">
                {workoutExercise.sets}x{workoutExercise.reps} {workoutExercise.weight && workoutExercise.weight + 'kg'}
            </div>
            <div className="col-4">
                <button className="btn btn-secondary mr-2" type="button"
                        onClick={() => editExercise(workoutExercise)}>E
                </button>
                <button className="btn btn-danger" type="button" onClick={removeExercise}>X</button>
            </div>
        </div>
    )
}


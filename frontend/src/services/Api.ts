import {get, post} from "./Http"
import {Session} from "../Session"

export interface UserDetails {
    userId: number,
    username: string,
    password: string
}

export interface Workout {
  id: number
  date: string,
  title: string,
  description: string,
  exercises: Array<WorkoutExercise>
}

export interface WorkoutExercise {
  name: string,
  weight: number,
  sets: number,
  reps: number,
}

export interface Exercise {
    id: number,
    name: string,
    description: string,
    mainMuscle: string,
}

export interface Stats {
    lastWorkout: string | null,
    workoutsLast30Days: number,
    commonExercise: String
}


export class Api {

    static login(email: string, password: string): Promise<Session> { //TODO: Look for a way to 'verify' against interface
        return post('/auth/login', {email: email, password: password})
            .then(res => {
                return res.json()
            })
    }

    static logcheck(): Promise<any> {
        return post('/auth/logcheck')
            .then(res => {
                return res.json()
            })
    }

    static logout(): Promise<Response> {
        return post('/auth/logout')
    }

    static register(email: string, password: string): Promise<any> {
        return post('/api/v1/users', {password: password, email: email})
            .then(res => {
                return res.json()
            })
    }

    static getWorkouts(userId: number): Promise<Array<Workout>> {
        return get(`/api/v1/users/${userId}/workouts`)
            .then(res => {
                return res.json()
            })
    }

    static addWorkout(userId: number, title: string, description: string, exercises: Array<object>): Promise<Workout> {
        return post(`/api/v1/users/${userId}/workouts`, {title: title, description: description, exercises: exercises})
            .then(res => {
                return res.json()
            })
    }

    static getExercises(): Promise<Array<Exercise>> {
        return get(`/api/v1/exercises`)
            .then(res => {
                return res.json()
            })
    }

    static getStats(userId: number): Promise<Stats> {
        return get(`/api/v1/users/${userId}/workouts/stats`,)
            .then(res => {
                return res.json()
            })
    }


}




import {get, post} from "./Http";

export interface UserDetails {
  userId: number,
  username: string,
  password: string
}


export interface Workout {
  date: string,
  title: string,
  description: string
}


export class Api {

  static login (email: string, password: string): Promise<UserDetails> { //TODO: Look for a way to 'verify' against interface
    return post('/auth/login', {email: email, password: password})
      .then(res => {
        return res.json()
      })
  }

  static logcheck (): Promise<any> {
    return post('/auth/logcheck')
      .then(res => {
        return res.json()
      })
  }

  static logout (): Promise<Response> {
    return post('/auth/logout')
  }

  static register (email: string, password: string): Promise<any> {
    return post('/api/v1/users', {password: password, email: email})
      .then(res => {
        return res.json()
      })
  }

  static getWorkouts (userId: number): Promise<Array<Workout>> {
    return get(`/api/v1/users/${userId}/workouts`)
      .then(res => {
        return res.json()
      })
  }

  static addWorkout (userId: number, title: string, description: string, date: Date): Promise<any> {
    return post(`/api/v1/user/${userId}/workouts`, {title: title, description: description, date: date})
      .then(res => {
        return res.json()
      })
  }

}




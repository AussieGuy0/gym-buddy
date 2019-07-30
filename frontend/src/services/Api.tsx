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

  static login (username: string, password: string): Promise<UserDetails> { //TODO: Look for a way to 'verify' against interface
    return post('/auth/login', {username: username, password: password})
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

  static register (username: string, password: string, email: string): Promise<any> {
    return post('/api/v1/users', {username: username, password: password, email: email})
      .then(res => {
        return res.json()
      })
  }

  static getWorkouts (userId: number): Promise<Workout> {
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

function get (url: string): Promise<Response> {
  return fetch(url, {
    method: 'GET',
    credentials: 'include'
  })
}

function post (url: string, data?: object): Promise<Response> {
  const headers = new Headers({'Content-Type': 'application/json'})
  return fetch(url, {
    method: 'POST',
    body: JSON.stringify(data),
    headers: headers,
    credentials: 'include'
  }).then((res) => {
    if (res.ok) {
      return res
    }
    throw new Error(`${res.status}`)
  })
}



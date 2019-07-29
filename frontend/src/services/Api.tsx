class Api {

  static login (username: string, password: string): Promise<any> {
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
    return post('/api/v1/user', {username: username, password: password, email: email})
      .then(res => {
        return res.json()
      })
  }

  static getWorkouts (userId: number): Promise<any> {
    return get('/api/v1/user/' + userId + '/workout')
      .then(res => {
        return res.json()
      })
  }

  static addWorkout (userId: number, title: string, description: string, date: Date): Promise<any> {
    return post('/api/v1/user/' + userId + '/workout', {title: title, description: description, date: date})
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

export default Api

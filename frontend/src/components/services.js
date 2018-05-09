class Services {

  static login (username, password) {
    return post('/auth/login', {username: username, password: password})
      .then(res => {
        return res.json()
      })
  }

  static logcheck () {
    return post('/auth/logcheck')
      .then(res => {
        return res.json()
      })
  }

  static logout () {
    return post('/auth/logout')
  }

  static register (username, password, email) {
    return post('/api/v1/user', {username: username, password: password, email: email})
      .then(res => {
        return res.json()
      })
  }

  static getWorkouts (userId) {
    return get('/api/v1/user/' + userId + '/workout')
      .then(res => {
        return res.json()
      })
  }

  static addWorkout (userId, title, description, date) {
    return post('/api/v1/user/' + userId + '/workout', {title: title, description: description, date: date})
      .then(res => {
        return res.json()
      })
  }

}

function get (url) {
  return fetch(url, {
    method: 'GET',
    credentials: 'include'
  })
}

function post (url, data) {
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
    throw new Error(res)
  })
}

export default Services

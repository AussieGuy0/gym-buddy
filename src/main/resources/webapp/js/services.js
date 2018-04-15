
class Services {

    login(username, password) {
        return post("/auth/login", {username: username, password: password})
            .then(res => {
                return res.json();
            })
    }

    logcheck() {
        return post("/auth/logcheck")
            .then(res => {
                return res.json()
            })
    }

    logout() {
        return post("/auth/logout")
    }

    register(username, password, email) {
        return post("/api/v1/user", {username: username, password: password, email: email})
            .then(res => {
                return res.json();
            })
    }

    getWorkouts(userId) {
        return get('/api/v1/user/' + userId + '/workout')
            .then(res => {
                return res.json();
            })
    }

}

function get(url) {
    return fetch(url, {
        method: 'GET',
        credentials: 'include'
    })
}

function post(url, data) {
    const headers = new Headers({'Content-Type': 'application/json'});
    return fetch(url, {
        method: 'POST',
        body: JSON.stringify(data),
        headers: headers,
        credentials: 'include'
    })
}
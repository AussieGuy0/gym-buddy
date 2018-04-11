
class Services {

    login(username, password) {
        return post("/auth/login", {username: username, password: password})
            .then(res => {
                return res.json();
            })
    }

    register(username, password, email) {
        return post("/api/v1/user", {username: username, password: password, email: email})
            .then(res => {
                return res.json();
            })
    }

}

function post(url, data) {
    const headers = new Headers({'Content-Type': 'application/json'});
    return fetch(url, {
        method: 'POST',
        body: JSON.stringify(data),
        headers: headers
    })
}
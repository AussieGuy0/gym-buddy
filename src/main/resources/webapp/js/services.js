
function login(username, password) {
    post("/auth/login", {username: username, password: password})
        .then(res => {
            return res.json();
        })
}

function post(url, data) {
    const headers = new Headers({'Content-Type': 'application/json'});
    return fetch(url, {
        method: 'POST',
        body: JSON.stringify(data),
        headers: headers
    })
}
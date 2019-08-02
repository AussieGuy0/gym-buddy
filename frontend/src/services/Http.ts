
export function get (url: string): Promise<Response> {
    return makeRequest(url, 'GET')
}

export function post (url: string, data?: object): Promise<Response> {
    return makeRequest(url, 'POST', data)
}

export function makeRequest(url: string, method: string, data?: object): Promise<Response> {
    const requestOptions: RequestInit = {
        method: method,
        credentials: 'include'
    }
    if (data != null) {
        requestOptions.body = JSON.stringify(data)
        requestOptions.headers = new Headers({'Content-Type': 'application/json'})
    }
    return fetch(url, requestOptions).then((res) => {
        if (res.ok) {
            return res
        }
        throw new Error(`${res.status}`)
    })

}

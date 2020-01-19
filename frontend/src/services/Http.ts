export interface ErrorDetails {
    statusCode: number
    message: string
    originalResponse: Response
}

export function get(url: string): Promise<Response> {
    return makeRequest(url, 'GET')
}

export function post(url: string, data?: object): Promise<Response> {
    return makeRequest(url, 'POST', data)
}

export async function makeRequest(url: string, method: string, data?: object): Promise<Response> {
    const requestOptions: RequestInit = {
        method: method,
        credentials: 'include'
    }
    if (data != null) {
        requestOptions.body = JSON.stringify(data)
        requestOptions.headers = new Headers({'Content-Type': 'application/json'})
    }

    //TODO: Need to try/catch the fetch
    const res = await fetch(url, requestOptions)
    if (res.ok) {
        return res
    }

    let json
    try {
        json = await res.json()
    } catch (err) {
        throw defaultError(res)
    }
    let error: ErrorDetails
    if (json.hasOwnProperty('message')) {
        error = {
            statusCode: json.statusCode,
            message: json.message,
            originalResponse: res
        }
    } else {
        error = defaultError(res)
    }
    throw error
}

function defaultError(res: Response): ErrorDetails {
    return {
        statusCode: res.status,
        message: 'Server Error',
        originalResponse: res
    }

}
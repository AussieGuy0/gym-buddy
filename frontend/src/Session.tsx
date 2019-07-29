interface Session {
    loggedIn: boolean, id?: number, username?: string
}

const session: Session = {
    loggedIn: false
}

export default session
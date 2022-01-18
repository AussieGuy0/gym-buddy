import React, { FormEvent, useEffect, useState } from "react"
import { Api } from "../services/Api"
import { ErrorDetails } from "../services/Http"
import { Session } from "../Session"
import { useHistory } from "react-router"

interface LoginProps {
  session: Session

  handleSuccessfulLogin(session: Session): void
}

const Login: React.FC<LoginProps> = ({ session, handleSuccessfulLogin }) => {
  const history = useHistory()
  const [email, setEmail] = useState("")
  const [password, setPassword] = useState("")
  const [loading, setLoading] = useState(false)
  const [error, setError] = useState<ErrorDetails | null>(null)
  const isLoggedIn = session.loaded && session.id !== null

  async function handleLogin(evt: FormEvent) {
    evt.preventDefault()
    setLoading(true)
    setError(null)
    try {
      const userDetails = await Api.login(email, password)
      handleSuccessfulLogin(userDetails)
    } catch (err) {
      // FIXME: Unsafe cast.
      setError(err as ErrorDetails)
    }
    setLoading(false)
  }

  useEffect(() => {
    if (isLoggedIn) {
      setTimeout(() => {
        history.push("/workouts")
      }, 500)
    }
  }, [session, history, isLoggedIn])

  return (
    <div>
      <h1>Login</h1>
      <div
        className={
          isLoggedIn
            ? "d-flex justify-content-center align-items-center"
            : "hidden"
        }
      >
        <h4>Logged in. Redirecting....</h4>
      </div>
      <form onSubmit={handleLogin} className={isLoggedIn ? "hidden" : ""}>
        <div className="mb-3">
          <label htmlFor="email">Email</label>
          <input
            className="form-control"
            type="email"
            id="email"
            value={email}
            onChange={(evt) => setEmail(evt.target.value)}
          />
        </div>
        <div className="mb-3">
          <label htmlFor="password">Password</label>
          <input
            className="form-control"
            type="password"
            value={password}
            id="password"
            onChange={(evt) => setPassword(evt.target.value)}
          />
        </div>
        <button disabled={loading} className="btn btn-primary">
          Login
        </button>
        {error && <span className="ms-2 text-danger">{error.message}</span>}
      </form>
    </div>
  )
}

export default Login

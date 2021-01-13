import React, { useEffect, useState, Fragment } from "react"
import {
  BrowserRouter as Router,
  Route,
  Switch,
  Redirect,
  Link,
} from "react-router-dom"
import Index from "./pages/Index"
import Login from "./pages/Login"
import Workouts from "./pages/workouts/Workouts"

import "./App.css"
import { Session } from "./Session"
import { Api } from "./services/Api"
import { LandingPage } from "./pages/LandingPage"
import { useInterval } from "./utils/hooks"

interface NavigationBarProps {
  session: Session

  handleSuccessfulLogout(): void
}

const NavigationBar: React.FC<NavigationBarProps> = ({
  session,
  handleSuccessfulLogout,
}) => {
  function logout() {
    Api.logout()
      .then(() => handleSuccessfulLogout())
      .catch((err) => {
        //TODO: Handle
        console.warn(err)
      })
  }

  const signedIn = session.id !== null

  return (
    <nav className="navbar navbar-expand-lg navbar-light bg-light">
      <div className="container">
        <Link className="navbar-brand" to="/">
          <img src="favicon.ico" height="32" width="32" alt="Gym buddy icon" />{" "}
          <span className="d-none d-sm-inline">Gym Buddy</span>
        </Link>
        <ul className="navbar-nav ms-auto">
          <li className="nav-item">
            {signedIn && (
              <Link to="/workouts" className="nav-link">
                Workouts
              </Link>
            )}
          </li>
          <li className="nav-item">
            {signedIn && (
              <span onClick={logout} className="nav-link">
                Logout
              </span>
            )}
          </li>
          <li className="nav-item">
            {!signedIn && (
              <Link to="/login" className="nav-link">
                Login
              </Link>
            )}
          </li>
        </ul>
      </div>
    </nav>
  )
}

interface PrivateRouteProps {
  session: Session
  children: React.ReactNode
}

const PrivateRoute: React.FC<PrivateRouteProps> = ({ session, children }) => {
  return (
    <Fragment>
      {session.id != null ? children : <Redirect to="/login" />}
    </Fragment>
  )
}

const App: React.FC = (props) => {
  const noSession: Session = { id: null, loaded: false }
  const [session, setSession] = useState<Session>(noSession)

  function handleSuccessfulLogin(session: Session): void {
    setSession({ ...session, loaded: true })
  }

  function handleSuccessfulLogout(): void {
    setSession({ id: null, loaded: true })
  }

  useInterval(() => {
    if (!session.loaded || session.id == null || !document.hasFocus) {
      return
    }
    Api.logcheck()
      .then((json) => {})
      .catch((err) => {
      // TODO: Don't log out, display modal
        handleSuccessfulLogout()
    })
  }, 30000)

  useEffect(() => {
    Api.logcheck()
      .then((json) => {
        handleSuccessfulLogin(json)
      })
      .catch((err) => {
        handleSuccessfulLogout()
      })
  }, [])
  return (
    <Router>
      {session.loaded && (
        <div>
          <NavigationBar
            session={session}
            handleSuccessfulLogout={handleSuccessfulLogout}
          />
          <div className="container">
            <Switch>
              <Route path="/" exact>
                {session.id ? <Index session={session} /> : <LandingPage />}
              </Route>
              <Route path="/login/">
                <Login
                  session={session}
                  handleSuccessfulLogin={handleSuccessfulLogin}
                />
              </Route>
              <PrivateRoute session={session}>
                <Route path="/" exact></Route>
                <Route path="/workouts">
                  <Workouts session={session} />
                </Route>
              </PrivateRoute>
            </Switch>
          </div>
        </div>
      )}
    </Router>
  )
}

export default App

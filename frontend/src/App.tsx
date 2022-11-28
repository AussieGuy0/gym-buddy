import React, { Fragment } from "react"
import { BrowserRouter as Router, Link, Redirect, Route, Switch } from "react-router-dom"
import Index from "./pages/Index"
import Login from "./pages/Login"
import Workouts from "./pages/workouts/Workouts"

import "./App.css"
import { Session } from "./Session"
import { Api } from "./services/Api"
import { LandingPage } from "./pages/LandingPage"
import { useUser } from "./hooks/User"

interface NavigationBarProps {
  handleSuccessfulLogout(): void;
}

const NavigationBar: React.FC<NavigationBarProps> = ({
  handleSuccessfulLogout,
}) => {
  const { session } = useUser();
  async function logout() {
    try {
      await Api.logout();
      handleSuccessfulLogout();
    } catch (err) {
      // TODO: Handle
      console.warn(err);
    }
  }

  const signedIn = session !== undefined;

  return (
    <nav className="navbar navbar-expand-lg navbar-light bg-light">
      <div className="container">
        <Link className="navbar-brand" to="/">
          <img src="favicon.ico" height="32" width="32" alt="Gym buddy icon" />{' '}
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
              <a href="#a" onClick={logout} className="nav-link">
                Logout
              </a>
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
  );
};

interface PrivateRouteProps {
  children: React.ReactNode;
}

const PrivateRoute: React.FC<PrivateRouteProps> = ({ children }) => {
  const { session } = useUser();
  return <Fragment>{session ? children : <Redirect to="/login" />}</Fragment>;
};

const App: React.FC = () => {
  const { session, error, isLoading, mutate } = useUser();

  function handleSuccessfulLogin(session: Session): void {
    mutate(session);
  }

  function handleSuccessfulLogout(): void {
    mutate(undefined);
  }

  // TODO: The 'disconnected' modal behaviour needs rethinking now with the move
  //       to SWR. Essentially we need to keep track that we did have a session
  //       but either are disconnected or got signed out.
  return (
    <Router>
      {!isLoading && (
        <div>
          <NavigationBar handleSuccessfulLogout={handleSuccessfulLogout} />
          <div className="container">
            <Switch>
              <Route path="/" exact>
                {session ? <Index /> : <LandingPage />}
              </Route>
              <Route path="/login/">
                <Login handleSuccessfulLogin={handleSuccessfulLogin} />
              </Route>
              <PrivateRoute>
                <Route path="/" exact></Route>
                <Route path="/workouts">
                  <Workouts />
                </Route>
              </PrivateRoute>
            </Switch>
          </div>
        </div>
      )}
    </Router>
  );
};

export default App;

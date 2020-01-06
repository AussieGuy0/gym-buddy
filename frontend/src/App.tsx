import React, {useEffect, useState, Fragment} from 'react'
import {BrowserRouter as Router, Route, Switch, Redirect} from 'react-router-dom'
import Index from "./pages/Index"
import Login from "./pages/Login"
import Workouts from "./pages/workouts/Workouts"
import {Navbar, Nav, NavbarBrand} from "react-bootstrap"
import NavbarCollapse from "react-bootstrap/NavbarCollapse"
import NavbarToggle from "react-bootstrap/NavbarToggle"
import NavLink from "react-bootstrap/NavLink"
import {LinkContainer} from 'react-router-bootstrap'

import './App.css'
import 'bootstrap/dist/css/bootstrap.min.css'
import {Session} from "./Session"
import {Api} from "./services/Api"


interface NavigationLinkProps {
    path: string,
    label: string
}

interface NavigationBarProps {
    session: Session,

    handleSuccessfulLogout(): void
}

const NavigationLink: React.FC<NavigationLinkProps> = ({path, label}) => {
    return (
        <LinkContainer to={path}>
            <NavLink>
                {label}
            </NavLink>
        </LinkContainer>
    )
}

const NavigationBar: React.FC<NavigationBarProps> = ({session, handleSuccessfulLogout}) => {
    function logout() {
        Api.logout()
            .then(() => handleSuccessfulLogout())
            .catch((err) => {
                console.warn(err)
            })
    }

    return (
        <Navbar bg="light" expand="sm">
            <div className="container">
                <LinkContainer to='/'>
                    <NavbarBrand>Gym Buddy</NavbarBrand>
                </LinkContainer>
                <NavbarToggle aria-controls="basic-navbar-nav"/>
                <NavbarCollapse id="basic-navbar-nav">
                    <Nav className="ml-auto">
                        {session.id !== null && (<NavigationLink path="/workouts" label="Workouts"/>)}
                        {session.id !== null && (<NavLink onClick={logout}>Logout</NavLink>)}
                        {session.id === null && (<NavigationLink path="/login" label="Login"/>)}
                    </Nav>
                </NavbarCollapse>
            </div>
        </Navbar>

    )
}

interface PrivateRouteProps {
    session: Session
    children: React.ReactNode
}

const PrivateRoute: React.FC<PrivateRouteProps> = ({session, children}) => {
    return (
        <Fragment>
            {session.id != null ? children : <Redirect to='/login'/>}
        </Fragment>
    )

}

const App: React.FC = (props) => {
    const noSession: Session = {id: null, loaded: false}
    const [session, setSession] = useState<Session>(noSession)

    function handleSuccessfulLogin(session: Session): void {
        setSession({...session, loaded: true})
    }

    function handleSuccessfulLogout(): void {
        setSession({id: null, loaded: true})
    }

    useEffect(() => {
        Api.logcheck()
            .then((json) => {
                handleSuccessfulLogin(json)
            })
            .catch((err) => {
                console.error(err)
            })
    }, [])
    return (
        <Router>
            { session.loaded &&
            (
                <div>
                    <NavigationBar session={session} handleSuccessfulLogout={handleSuccessfulLogout}/>
                    <Switch>
                        <div className="container">
                            <Route path="/" exact>
                                <Index/>
                            </Route>
                            <Route path="/login/">
                                <Login handleSuccessfulLogin={handleSuccessfulLogin}/>
                            </Route>
                            <PrivateRoute session={session}>
                                <Route path="/workouts">
                                    <Workouts session={session}/>
                                </Route>
                            </PrivateRoute>
                        </div>
                    </Switch>
                </div>
            )
            }

        </Router>
    )
}

export default App

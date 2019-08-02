import React, {useContext, useState} from 'react';
import { BrowserRouter as Router, Route } from 'react-router-dom';
import Index from "./pages/Index";
import Login from "./pages/Login";
import Register from "./pages/Register";
import Workouts from "./pages/Workouts";
import {Navbar, Nav, NavbarBrand} from "react-bootstrap"
import NavbarCollapse from "react-bootstrap/NavbarCollapse";
import NavbarToggle from "react-bootstrap/NavbarToggle";
import NavLink from "react-bootstrap/NavLink";
import { LinkContainer } from 'react-router-bootstrap'

import './App.css';
import 'bootstrap/dist/css/bootstrap.min.css';
import {Session} from "./Session";
import {Api} from "./services/Api";


interface NavigationLinkProps {
   path: string,
   label: string
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

const NavigationBar: React.FC<any> = ({session, handleSuccessfulLogout}) => {
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
                <NavbarBrand>Gym Buddy</NavbarBrand>
                <NavbarToggle aria-controls="basic-navbar-nav"/>
                <NavbarCollapse id="basic-navbar-nav">
                    <Nav className="ml-auto">
                        {session.id !== null && (<NavigationLink path="/workouts" label="Workouts" />)}
                        {session.id !== null && (<NavLink onClick={logout}>Logout</NavLink>)}
                        {session.id === null && (<NavigationLink path="/login" label="Login" />)}
                        {session.id === null && (<NavigationLink path="/register" label="Register" />)}
                    </Nav>
                </NavbarCollapse>
            </div>
        </Navbar>

    )
}

const App: React.FC = (props) => {
    const noSession: Session = {id: null}
    const [session, setSession] = useState<Session>(noSession)
    function handleSuccessfulLogin(session: Session): void {
        setSession(session)
    }
    function handleSuccessfulLogout(): void {
        setSession(noSession)
    }
    return (
        <Router>
            <div>
                <NavigationBar session={session} handleSuccessfulLogout={handleSuccessfulLogout}/>
                <div className="container">
                   <Route path="/" exact component={Index}/>
                   <Route path="/login/" render={() => (<Login handleSuccessfulLogin={handleSuccessfulLogin}/>)}/>
                   <Route path="/register" component={Register}/>
                   <Route path="/workouts" render={() => (<Workouts session={session} />)}/>
               </div>
          </div>
      </Router>
  );
}

export default App;

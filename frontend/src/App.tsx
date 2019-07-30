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

const NavigationBar: React.FC<any> = ({session}) => {
    return (
        <Navbar bg="light" expand="sm">
            <div className="container">
                <NavbarBrand>Gym Buddy</NavbarBrand>
                <NavbarToggle aria-controls="basic-navbar-nav"/>
                <NavbarCollapse id="basic-navbar-nav">
                    <Nav className="ml-auto">
                        {session.loggedIn && (<NavigationLink path="/workouts" label="Workouts" />)}
                        <NavigationLink path="/login" label="Login" />
                        <NavigationLink path="/register" label="Register" />
                        {session.loggedIn && (<NavigationLink path="/logout" label="Logout" />)}
                    </Nav>
                </NavbarCollapse>
            </div>
        </Navbar>

    )
}

const App: React.FC = () => {
    const [session, setSession] = useState<Session>({loggedIn: false})
    return (
        <Router>
            <div>
                <NavigationBar session={session}/>

                <div className="container">

                   <Route path="/" exact component={Index}/>
                   <Route path="/login/" component={Login}/>
                   <Route path="/register" component={Register}/>
                   <Route path="/workouts" component={Workouts}/>
               </div>
          </div>
      </Router>
  );
}

export default App;

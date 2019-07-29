import React from 'react';
import { BrowserRouter as Router, Route, Link } from 'react-router-dom';
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

const NavigationBar: React.FC = () => {
    return (
        <Navbar bg="light" expand="sm">
            <NavbarBrand>Gym Buddy</NavbarBrand>
            <NavbarToggle aria-controls="basic-navbar-nav"/>
            <NavbarCollapse id="basic-navbar-nav">
                <Nav className="mr-auto">
                    <LinkContainer to="/workouts">
                        <NavLink>
                            Workouts
                        </NavLink>
                    </LinkContainer>
                    <LinkContainer to="/login">
                        <NavLink>
                            Login
                        </NavLink>
                    </LinkContainer>
                    <LinkContainer to="/register">
                        <NavLink>
                            Register
                        </NavLink>
                    </LinkContainer>
                    <LinkContainer to="/logout">
                        <NavLink>
                            Logout
                        </NavLink>
                    </LinkContainer>
                </Nav>
            </NavbarCollapse>
        </Navbar>

    )
}

const App: React.FC = () => {
    return (
        <Router>
            <div>
               <NavigationBar/> 
                
              <Route path="/" exact component={Index}/>
              <Route path="/login/" component={Login}/>
              <Route path="/register" component={Register}/>
              <Route path="/workouts" component={Workouts}/>
          </div>
      </Router>
  );
}

export default App;

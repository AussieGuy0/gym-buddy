import React, {FormEvent, useState} from "react";
import {Api} from "../services/Api";

const Login: React.FC<any> = ({handleSuccessfulLogin}) => {
    const [username, setUsername] = useState('');
    const [password, setPassword] = useState('');
    function handleLogin (evt: FormEvent) {
        evt.preventDefault()
        Api.login(username, password)
            .then((userDetails) => {
                handleSuccessfulLogin(userDetails)
            }).catch((err) => {
                //TODO: Handle error message somewhere
                console.warn(err)
        })
    }

    return (
        <div>
            <h1>Login</h1>
            <form onSubmit={handleLogin}>
                <label>
                    Username
                    <input type='text' value={username} onChange={(evt) => setUsername(evt.target.value)}/>
                </label>
                <label>
                    Password
                    <input type='password' value={password} onChange={(evt) => setPassword(evt.target.value)}/>
                </label>
                <button className='btn btn-primary'>
                    Login
                </button>
            </form>
        </div>
    );
}

export default Login;

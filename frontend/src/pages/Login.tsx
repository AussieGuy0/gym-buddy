import React, {useState} from "react";
import {Api} from "../services/Api";

const Login: React.FC = () => {
    const [username, setUsername] = useState('');
    const [password, setPassword] = useState('');
    function handleLogin () {
        Api.login(username, password)
            .then((userDetails) => {
                //TODO: Save login details somewhere!
            }).catch((err) => {
                //TODO: Handle error messasge somewhere
                console.warn(err)
        })
    }

    return (
        <div>
            <h1>Login</h1>
            <form>
                <label>
                    Username
                    <input type='text' value={username} onChange={(evt) => setUsername(evt.target.value)}/>
                </label>
                <label>
                    Password
                    <input type='password' value={password} onChange={(evt) => setPassword(evt.target.value)}/>
                </label>
                <button className='btn btn-primary' onClick={handleLogin}>
                    Login
                </button>
            </form>
        </div>
    );
}

export default Login;

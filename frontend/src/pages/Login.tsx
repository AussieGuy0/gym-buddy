import React, {FormEvent, useState} from "react";
import {Api} from "../services/Api";

const Login: React.FC<any> = ({handleSuccessfulLogin}) => {
    const [email, setEmail] = useState('');
    const [password, setPassword] = useState('');
    function handleLogin (evt: FormEvent) {
        evt.preventDefault()
        Api.login(email, password)
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
                <div className='form-group'>
                    <label htmlFor='email'> Email</label>
                    <input className='form-control' type='email' id='email' value={email} onChange={(evt) => setEmail(evt.target.value)}/>
                </div>
                <div className='form-group'>
                    <label htmlFor='password'>Password</label>
                    <input className='form-control' type='password' value={password} id='password' onChange={(evt) => setPassword(evt.target.value)}/>
                </div>
                <button className='btn btn-primary'>
                    Login
                </button>
            </form>
        </div>
    );
}

export default Login;

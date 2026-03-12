import React, { useContext, useState } from 'react'
import './Login.css'
import { loginUser } from '../../service/AuthService'
import { Link, useNavigate } from 'react-router-dom'
import { toast } from 'react-toastify'
import { StoreContext } from '../../context/StoreContex'

const Login = () => {

    const {setActive}=useContext(StoreContext);
        setActive('register');

    const {setToken,loadCartData}=useContext(StoreContext);
    const [data,setData]=useState(
        {
            email:'',
            password:''
        }
    )
    const navigate=useNavigate();

    const onChangeHandler=(event)=>{
        const name=event.target.name;
        const value=event.target.value;
        setData(data=>({...data, [name]:value}));

    }

    const onSubmitHandler=async(event)=>{
         event.preventDefault();
        try {
            const response =await loginUser(data);
                if(response.status===200){
                        toast.success('Login successfull!! , Welcome to our application');
                        setToken(response.data.token);
                        localStorage.setItem('token', response.data.token);
                        await loadCartData( response.data.token);
                        navigate("/")
                }
            else{
                    toast.error('Login failed, invalid credential');
                }
                        
            } catch (error) {
                    toast.error('Login failed, invalid credential');                        
                 }

    }


  return (
    <div className="login-background-div bg-light d-flex align-items-center justify-content-center mt-2">
    <div className="card shadow-lg w-100" style={{"maxWidth": "480px"}}>
        <div className="card-body">
            <div className="text-center">
                <h1 className="card-title h3">log in</h1>
                <p className="card-text text-muted">Log in below to access your account</p>
            </div>
            <div className="mt-4">
                <form onSubmit={onSubmitHandler}>
                    <div className="mb-4">
                        <label htmlFor="email" className="form-label text-muted">Email Address</label>
                        <input type="email" className="form-control" id="email" placeholder="Email Address" name="email" onChange={onChangeHandler} value={data.email} required/>
                    </div>
                    <div className="mb-4">
                        <label htmlFor="password" className="form-label text-muted">Password</label>
                        <input type="password" className="form-control" id="password" placeholder="Password" name="password" onChange={onChangeHandler} value={data.password}  required/>
                    </div>
                    <div className="d-grid">
                        <button type="submit" className="btn btn-dark btn-lg">Log in</button>
                    </div>
                    <p className="text-center text-muted mt-4">Don't have an account yet?
                        <Link  className="text-decoration-none" to="/register">Sign up</Link>.
                    </p>
                </form>
            </div>
        </div>
    </div>
    </div>
  )
}

export default Login

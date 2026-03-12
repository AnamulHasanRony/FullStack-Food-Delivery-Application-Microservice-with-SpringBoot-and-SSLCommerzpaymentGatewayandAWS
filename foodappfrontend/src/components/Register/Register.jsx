import React, { useContext, useState } from 'react'
import './Register.css'
import axios from 'axios';
import { toast } from 'react-toastify'
import { Link, useNavigate } from 'react-router-dom';
import { registerUser } from '../../service/AuthService';
import { StoreContext } from '../../context/StoreContex';

const Register = () => {
    const {setActive}=useContext(StoreContext);
    setActive('register');

    const[data,setData]=useState(
        {
            name:'',
            email:'',
            password:''
        });

    const navigate=useNavigate();

        const onChangeHandler=(event)=>{
            const name=event.target.name;
            const value=event.target.value;
            setData(data=>({...data,[name]: value}));
        }

        const onSubmitHandler=async (event)=>{
            event.preventDefault();
            console.log(data);
            try {
                const response =await registerUser(data);
                if(response.status===201){
                    toast.success('Registration successfull!! , please log in');
                    navigate("/login")
                }
                else{
                    toast.error('Register failed, try leter');
                }
                
            } catch (error) {
                toast.error('Email already exist, try with new email');
                
            }

        }

  return (
    <div className="background-div bg-light d-flex align-items-center justify-content-center mt-2">
    <div className="card shadow-lg w-100" style={{"maxWidth": "480px"}}>
        <div className="card-body">
            <div className="text-center">
                <h1 className="card-title h3">Sign up</h1>
                <p className="card-text text-muted">Create  your account</p>
            </div>
            <div className="mt-4">
                <form onSubmit={onSubmitHandler}>
                    <div className="mb-4">
                        <label htmlFor="name" className="form-label text-muted">Full Name</label>
                        <input type="text" className="form-control" id="name" placeholder="Enter your full name" name="name" required onChange={onChangeHandler} value={data.name}/>
                    </div>
                    <div className="mb-4">
                        <label htmlFor="email" className="form-label text-muted">Email Address</label>
                        <input type="email" className="form-control" id="email" placeholder="Enter your Email Address" name="email" onChange={onChangeHandler} value={data.email} required />
                    </div>
                    <div className="mb-4">
                        <label htmlFor="password" className="form-label text-muted">Password</label>
                        <input type="password" className="form-control" id="password" placeholder="Enter a strong Password" name="password" required onChange={onChangeHandler} value={data.password}/>
                    </div>
                    <div className="d-grid">
                        <button type="submit" className="btn btn-dark btn-lg">Sign up</button>
                    </div>
                    <p className="text-center text-muted mt-4">Already have an accoun?
                        <Link className="text-decoration-none" to="/login">Log in</Link>.
                    </p>
                </form>
            </div>
        </div>
    </div>
    </div>
  )
}

export default Register

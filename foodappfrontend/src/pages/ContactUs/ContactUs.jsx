import React, { useContext, useState } from 'react'
import './ContactUs.css'
import { toast } from 'react-toastify';
import { submitToContactForm } from '../../service/ContactUs';
import { StoreContext } from '../../context/StoreContex';

const ContactUs = () => {

    const {setActive}=useContext(StoreContext);
    setActive('contact');

    const [data,setData]=useState({
            name:'',
            email:'',
             message:''
        });

    
        const onChangeHandler=(event)=>{
            const name=event.target.name;
            const value=event.target.value;
            setData(date=>({...data, [name]:value}))
        }
        const onSubmitHandler=async(event)=>{
            event.preventDefault();
            console.log(data);


            try {
            
            const res = await submitToContactForm(data);
            toast.success("successfully submitted contact form");

            } catch (err) {
                    console.error(err);
                    toast.error("Unable to submit contact form");
                }

        }
  return (
    
    <div className='contact-background-div card-body' >
    <h1 className="text-center fw-bold">Contact Us</h1>
    <div className="row">
        <div className="col-md-8 offset-md-2">
            <form className="contact-form" onSubmit={onSubmitHandler}>
                <div className="form-group fw-bold">
                    <label htmlFor="name">Name</label>
                    <input type="text" id="name" name="name" placeholder="enter your name" value={data.name} onChange={onChangeHandler} required/>
                </div>
                <div className="form-group fw-bold">
                    <label htmlFor="email">Email</label>
                    <input type="email" id="email" name="email" placeholder="enter your email" value={data.email} onChange={onChangeHandler} required/>
                </div>
                <div className="form-group fw-bold">
                    <label htmlFor="message">Message</label>
                    <textarea id="message" name="message" placeholder="enter your message" value={data.message} onChange={onChangeHandler} required></textarea>
                </div>
                <button type="submit">Submit</button>
            </form>
        </div>
    </div>
</div>
  )
}

export default ContactUs

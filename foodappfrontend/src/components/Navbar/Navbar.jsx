import React, { useContext, useState } from 'react'
import './Navbar.css'
import { assets } from '../../assets/assets'
import { Link, useNavigate } from 'react-router-dom'
import { StoreContext } from '../../context/StoreContex'


const Navbar = () => {
    
    const {quantity,token,setToken,setQuantity,active, setActive}=useContext(StoreContext);
    const uniqueItems=Object.values(quantity).filter(qty=>qty>0).length;
    const navigate=useNavigate();
    const logout=()=>{
        localStorage.removeItem('token');
        setToken("");
        setQuantity({});

        navigate("/");
    }
  return (
    <div>

    
    <nav className="navbar navbar-expand-lg bg-body-tertiary"  >
        <div className="container">
           <Link to={"/"}  style={{"textDecoration":"none"}}> <img src={assets.logo} alt="" className='mx-4' height={80} width={80} onClick={()=>setActive('home')}/>
           <b><h6 className=" navbar-brand fw-bold  brand-title" style={{"color":"green"}} onClick={()=>setActive('home')} >Food Delivery Application</h6></b></Link>
            <button className="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
            <span className="navbar-toggler-icon"></span>
            </button>
            <div className="collapse navbar-collapse" id="navbarSupportedContent">
            <ul className="navbar-nav me-auto mb-2 mb-lg-0">
                <li className="nav-item">
                <Link className={active==='home' ?"nav-link active fw-bold": "nav-link"} aria-current="page" to="/" onClick={()=>setActive('home')}>Home</Link>
                </li>
                <li className="nav-item">
                <Link className={active==='explore' ?"nav-link active fw-bold": "nav-link"} to="/explore" onClick={()=>setActive('explore')}>Explore</Link>
                </li>
                <li className="nav-item">
                <Link className={active==='contact' ?"nav-link active fw-bold": "nav-link"} to="/contact" onClick={()=>setActive('contact')}>ContactUs</Link>
                </li>
            </ul>
            <div className="d-flex align-items-center gap-4">
                <Link to={"/cart"}>
                <div className="position-relative">
                    <img src={assets.cart} alt="" height={40} width={40} className='position-relative'/>
                    <span className="position-absolute top-0 start-100 translate-middle badge rounded-pill bg-warning">{uniqueItems}</span>
                </div>
                </Link>{
                    !token?
                    <>
                      <button className='btn btn-outline-primary' onClick={()=>navigate("/login")}>Login</button>
                     <button className='btn btn-outline-success' onClick={()=>navigate("/register")}>Register</button>
                     </>:
                    <div className='dropdown test-end'>
                        <a href="" className='d-block link-body-emphasis text-decoration-none dropdown-toggle' data-bs-toggle="dropdown" aria-expanded="false">
                            <img src="https://media.geeksforgeeks.org/wp-content/uploads/20221210180014/profile-removebg-preview.png" alt="" width={40} height={40} className="rounded-circle"/>
                            <ul className='dropdown-menu text-small'>
                                <li className='dropdown-item' onClick={()=>navigate('/order/history')}>orders</li>
                                <li className='dropdown-item' onClick={logout}>logout</li>
                                
                            </ul>
                        </a>
                    </div>
                }
                
            </div>
            </div>
        </div>
    </nav> 
</div>
  )
}

export default Navbar

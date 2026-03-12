import React, { useContext } from 'react'
import './Header.css'
import { Link } from 'react-router-dom'
import { StoreContext } from '../../context/StoreContex';

const Header = () => {
  const {setActive}=useContext(StoreContext);
      
  return (
    <div className="p-5 mb-4 bg-light rounded-4 mt-1 header">
        <div className="container-fluid py-5">
            <h1 className="display-5 fw-bold text-dark">Order your favorite food here</h1>
            <p className="col-md-8 fs-4 text-dark">Discover the best food and drinks in our app</p>
            <Link to="/explore" className="btn btn-primary" onClick={()=>setActive('explore')}>Explore</Link>
        </div>
    </div>
  )
}

export default Header

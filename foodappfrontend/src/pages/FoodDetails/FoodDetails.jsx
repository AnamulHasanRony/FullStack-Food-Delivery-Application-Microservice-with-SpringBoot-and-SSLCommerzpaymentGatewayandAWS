import React, { useContext, useEffect, useState } from 'react'
import './FoodDetails.css'
import { useNavigate, useParams } from 'react-router-dom'
import axios from 'axios';
import {toast} from 'react-toastify'
import { getFoodData } from '../../service/FoodService';
import { StoreContext } from '../../context/StoreContex';

const FoodDetails = () => {
    const {id}=useParams();
    const [foodData, setFoodData]=useState({});

    useEffect(()=>{
        const loadDoodData=async()=>{
          try {
            const responseFoodData=await getFoodData(id);
            setFoodData(responseFoodData);
          } catch (error) {
            toast.error("Error while geting data from api calls")
          }
        }
       loadDoodData();
    },[id])

    const { increaseQuantity}=useContext(StoreContext);
    const navigate=useNavigate();

    const addToCart=()=>{
        increaseQuantity(foodData.id);
        navigate("/cart");
    }

  return (
        <div className="container mt-5">
            <div className="row">
                <div className="col-md-6 mb-4">
                    <img src={foodData.imageUrl} alt="Product" className="img-fluid rounded mb-3 product-image" id="mainImage"/>
                    
                </div>

                <div className="col-md-6">
                    <h1 className="mb-3 fw-bold">{foodData.name}</h1>
                    <div className="mb-3">
                        <span className="h4 me-2">Tk. {foodData.price}</span>
                    </div>
                
                    <p className="mb-4">{foodData.description}</p>
                    <div className="mb-4">
                        <h5>Category:</h5>
                        <div className="btn-group badge text-bg-warning fs-5"> {foodData.category} </div>
                    </div>
                    
                    <button className="btn btn-primary btn-lg mb-3 me-2" onClick={()=> addToCart()}>
                            <i className="bi bi-cart-plus" ></i> Add to Cart
                        </button>
                   
                    
                </div>
            </div>
        </div>

        
  )
}

export default FoodDetails

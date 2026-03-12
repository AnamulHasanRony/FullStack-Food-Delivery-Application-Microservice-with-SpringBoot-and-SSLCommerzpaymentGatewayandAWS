import React, { useContext } from 'react'
import { Link } from 'react-router-dom';
import { StoreContext } from '../../context/StoreContex';

const FoodItem = ({name,price, description,category,image,id}) => {

    const {increaseQuantity, decreaseQuantity}=useContext(StoreContext);
    
  return (
   <div className="col-12 col-sm-6 col-md-4 col-lg-3 mb-4 d-flex justify-content-center">
                <div className="card" style={{"maxwidth": "320px","textDecoration":"none"}}>
                   <Link to={`/food/${id}`}> <img src={image} className="card-img-top" alt="Product Image"/></Link>
                    <div className="card-body">
                        <h5 className="card-title fw-bold">{name}</h5>
                        <p className="card-text">{description}</p>
                        <div className="d-flex justify-content-between align-items-center">
                            <span className="h5 mb-0 fw-bold">Tk. {price}</span>
                            <div className='fw-bold'>
                                {category}
                            </div>
                        </div>
                    </div>
                    <div className="card-footer d-flex justify-content-between bg-light">
                        <Link to={`/food/${id}`}><button className="btn btn-primary btn-sm">view details</button></Link>
                        <button className="btn btn-warning btn-sm" onClick={()=>increaseQuantity(id)}>Add to Cart</button>

                    </div>
                </div>
          </div>
  )
}

export default FoodItem;

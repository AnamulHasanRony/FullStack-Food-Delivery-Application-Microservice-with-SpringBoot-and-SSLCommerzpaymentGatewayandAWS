import React, { useContext } from 'react'
import './Cart.css'
import { StoreContext } from '../../context/StoreContex'
import { Link, useNavigate } from 'react-router-dom';
import { calculateCartItems } from '../../Utils/CartUtils';

const Cart = () => {

    const {setActive}=useContext(StoreContext);
        setActive('');

    const {foodList, increaseQuantity, decreaseQuantity, quantity,removeFoodFromQuantity}=useContext(StoreContext);
    const cartItems= foodList.filter(food=>quantity[food.id]>0);

    const {subTotal,deliveryCharge, tax,  total}=calculateCartItems(cartItems, quantity);

    const navigate=useNavigate();
  return (
    <div className="container py-5">
    <h1 className="mb-5">Your food Cart</h1>
    <div className="row">
        <div className="col-lg-8">
            {
                cartItems.length===0?
                
                (<div>your cart is empty</div>)
                :
                
                (<div className="card mb-4">
                <div className="card-body">
                    {cartItems.map((food)=>(
                        <div>
                      <div className="row cart-item mb-3">
                        <div className="col-md-3">
                            <img src={food.imageUrl} alt={food.name} className="img-fluid rounded" />
                        </div>
                        <div className="col-md-5">
                            <h5 className="card-title">{food.name}</h5>
                            <p className="text-muted">Category: {food.category}</p>
                        </div>
                        <div className="col-md-2">
                            <div className="input-group">
                                <button className="btn btn-outline-secondary btn-sm" type="button" onClick={()=>decreaseQuantity(food.id)}>-</button>
                                <input style={{"maxWidth":"100px"}} type="text" className="form-control  form-control-sm text-center quantity-input" value={quantity[food.id]}/>
                                <button className="btn btn-outline-secondary btn-sm" type="button" onClick={()=>increaseQuantity(food.id)}>+</button>
                            </div>
                        </div>
                        <div className="col-md-2 text-end">
                            <p className="fw-bold">Tk. {food.price}</p>
                            <button className="btn btn-sm btn-outline-danger" onClick={()=>removeFoodFromQuantity(food.id)}>
                                    <i className="bi bi-trash"></i>
                                </button>
                        </div>
                        
                    </div>
                    <hr/>
                    </div>
                    

                    ))}
                   
                </div>
            </div>)
            }
            
            <div className="text-start mb-4">
                <Link to="/explore" className="btn btn-outline-primary">
                    <i className="bi bi-arrow-left me-2"></i>Continue Shopping
                </Link>
            </div>
        </div>
        <div className="col-lg-4">
            <div className="card cart-summary">
                <div className="card-body">
                    <h5 className="card-title mb-4">Order Summary</h5>
                    <div className="d-flex justify-content-between mb-3">
                        <span>Subtotal</span>
                        <span>Tk. {subTotal.toFixed(2)}</span>
                    </div>
                    <div className="d-flex justify-content-between mb-3">
                        <span>Delivery Charge</span>
                        <span>Tk. {deliveryCharge.toFixed(2)} </span>
                    </div>
                    <div className="d-flex justify-content-between mb-3">
                        <span>Tax</span>
                        <span>Tk. {tax.toFixed(2)}</span>
                    </div>
                    <hr/>
                    <div className="d-flex justify-content-between mb-4">
                        <strong>Total</strong>
                        <strong>Tk. {total.toFixed(2)} </strong>
                    </div>
                    <button className="btn btn-primary w-100" disabled={cartItems.length===0} onClick={()=>navigate('/order/placeOrder')}>Proceed to Checkout</button>
                </div>
            </div>
           
        </div>
    </div>
</div>
  )
}

export default Cart

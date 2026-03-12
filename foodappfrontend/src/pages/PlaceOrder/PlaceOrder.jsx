import React, { useContext, useEffect, useState } from 'react'
import './PlaceOrder.css'
import { StoreContext } from '../../context/StoreContex';
import { calculateCartItems } from '../../Utils/CartUtils';
import axios from 'axios';
import { toast } from 'react-toastify';
import { useNavigate, useParams, useSearchParams } from 'react-router-dom';
import { createOrderWithPayment } from '../../service/OrderService';
import { VerifyOrderPayment } from '../../service/PaymentService';
import { ClearAllCartItems } from '../../service/CartService';



const PlaceOrder = () => {

        const {foodList,quantity,token, clearCart,setToken,loadCartData}=useContext(StoreContext);
        const cartItems= foodList.filter(food=>quantity[food.id]>0);
    
        const {subTotal,deliveryCharge, tax,  total}=calculateCartItems(cartItems, quantity);



        const [data,setData]=useState({
            customerName:'',
            customerPhoneNo:'',
             customerAddress:'',
             customerCountry:'Bangladesh',
               customerCity:'Mirpur',
              customerState:'Dhaka'

        });

        const {setActive}=useContext(StoreContext);
            setActive('');

        //if tranId is req param
        const navigate = useNavigate();
        const [searchParams] = useSearchParams();

        const tranId = searchParams.get("tranId");

        const onChangeHandler=(event)=>{
            const name=event.target.name;
            const value=event.target.value;
            setData(date=>({...data, [name]:value}))
        }
        const onSubmitHandler=async(event)=>{
            event.preventDefault();
            console.log(data);


            try {
            
            const res = await createOrderWithPayment(token,data);
            window.location.href = res.data.paymentUrl;

            } catch (err) {
                    console.error(err);
                    toast.error("Unable to create place order");
                }

        }


        ///if tran_id exist then need to show toast veryfy message and data need tobe prefilled
        useEffect(() => {
            if (tranId) {
                verifyPayment(tranId);
            }
        }, [tranId]);


        const verifyPayment = async (tranId) => {
    try {
        
        const res = await VerifyOrderPayment(token, tranId);

        if (res.data.status === "success") {
            const order = res.data.order;

        setData({
          customerName: order.customerName,
          customerPhoneNo: order.customerPhoneNo,
          customerAddress: order.customerAddress,
          customerCountry: order.customerCountry || "Bangladesh",
          customerCity: order.customerCity || "Mirpur",
          customerState: order.customerState || "Dhaka"
        });
        
        toast.success("Payment successful to the order!!");
        
        setTimeout(async() => {
          
          navigate("/order/history");

          try {
                    await ClearAllCartItems(token);
                    await loadCartData(token);

          } catch (error) {
            console.log(error);
            
          }

        }, 2500);



      } else {
        
        toast.error("Payment failed to the order!!");
      }

    } catch (err) {
       toast.error("Payment verification failed");
    }
  };

    
  return (
    <div className="container">
    
    <div className="row">
        <div className="col-md-4 order-md-2 mb-4 mt-4">
            <h4 className="d-flex justify-content-between align-items-center mb-3">
                <span className="text-primary">Your cart</span>
                <span className="badge bg-primary rounded-pill">{cartItems.length}</span>
            </h4>
            <ul className="list-group mb-3 sticky-top">
                {cartItems.length===0?(<div> No items</div>):
                    (  cartItems.map((food)=>(
                         <li key={food.id} className="list-group-item d-flex justify-content-between lh-condensed">
                        <div>
                            <h6 className="my-0">{food.name}</h6>
                            <small className="text-muted">Quantity: {quantity[food.id]}</small>
                        </div>
                        <span className="text-muted">Tk. {(food.price* quantity[food.id]).toFixed(2)}</span>
                    </li>

                    ))
                       
                    )
                 }

                 <li className="list-group-item d-flex justify-content-between">
                    <span>Tax (BDT)</span>
                    <span className="text-muted">Tk. {tax.toFixed(2)}</span>

                </li>

                <li className="list-group-item d-flex justify-content-between">
                    <span>Delivery Charge (BDT)</span>
                    <span className="text-muted">Tk. {deliveryCharge.toFixed(2)}</span>
                </li>
                
                <li className="list-group-item d-flex justify-content-between">
                    <span>Total (BDT)</span>
                    <strong>Tk. {total.toFixed(2)}</strong>
                </li>
            </ul>
            
        </div>
        <div className="col-md-8 order-md-1 mt-4">
            <h4 className="mb-3 text-center fw-bold">Delivery address</h4>
            <hr/>
            <form className="needs-validation" onSubmit={onSubmitHandler} >
                <div className="row">
                    <div className="col-md-6 mb-3">
                        <label htmlFor="firstName">Full name</label>
                        <input type="text" className="form-control" id="firstName" placeholder="Anamul" name="customerName" value={data.customerName}  onChange={onChangeHandler} required/>
                    </div>
                    
                </div>
                
                <div className="mb-3">
                    <label htmlFor="phone">Phone Number </label>
                    <input type="number" className="form-control" id="number" placeholder="01700000000" name="customerPhoneNo" value={data.customerPhoneNo} onChange={onChangeHandler} required/>
                </div>
                <div className="mb-3">
                    <label htmlFor="address">Address</label>
                    <input type="text" className="form-control" id="address" placeholder="Holding no. 1284, Kazipara, Mirpur, Dhaka" name="customerAddress" value={data.customerAddress} onChange={onChangeHandler} required/>
                </div>
                
                <div className="row">
                    <div className="col-md-5 mb-3">
                        <label htmlFor="country">Country</label>
                        <select className="custom-select d-block w-100" id="country"  name="customerCountry" value={data.customerCountry} onChange={onChangeHandler}  required="">
                            <option value="Bangladesh">Bangladesh</option>
                        </select>
                    </div>
                    <div className="col-md-4 mb-3">
                        <label htmlFor="district">District</label>
                        <select className="custom-select d-block w-100" id="district" name="customerState" value={data.customerState} onChange={onChangeHandler}  required="">
                            <option value="Dhaka">Dhaka</option>
                        </select>
                    </div>

                    <div className="col-md-4 mb-3">
                        <label htmlFor="city">City</label>
                        <select className="custom-select d-block w-100" id="city" name="customerCity" value={data.customerCity} onChange={onChangeHandler}  required="">
                            <option value="Mirpur">Mirpur</option>
                            <option value="Banani">Banani</option>
                            <option value="Uttora">Uttora</option>
                            <option value="Mohammadpur">Mohammadpur</option>
                            <option value="Dhanmondi">Dhanmondi</option>
                        </select>
                    </div>
                    
                </div>
                <hr className="mb-4" />
                <button className="btn btn-primary btn-lg btn-block" type="submit">Continue to checkout</button>
            </form>
        </div>
    </div>
    
</div>
  )
}

export default PlaceOrder

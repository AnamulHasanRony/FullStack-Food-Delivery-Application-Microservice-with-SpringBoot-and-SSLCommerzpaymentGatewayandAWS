import axios from 'axios';
import React, { useContext, useEffect, useState } from 'react'
import { useSearchParams } from 'react-router-dom'
import { StoreContext } from '../../context/StoreContex';
import'./OrderHistory.css'
import { getOrderHistory } from '../../service/OrderService';

const OrderHistory = () => {
    const {token, setActive}=useContext(StoreContext);
    setActive('');
    const [message,setMessage]=useState("verifying payment..");

    const [orderHistoryData, setOrderHistoryData]=useState([]);
    
    useEffect(()=>{
        const loadAllOrderHistory=async()=>{
            
        const res= await getOrderHistory(token);
        console.log(res.data);
        setOrderHistoryData(res.data);
           
    };
        loadAllOrderHistory();

    },[]);

  return (
    <div>
      <div className="container mt-4">
    <div className="p-5 mb-4 bg-light rounded-3">
      <h2 className='text-center display-5 fw-bold'>Order History</h2>
      <hr/>
    </div>

        <table className="table table-bordered table-hover table-striped align-middle">
        <thead className="table-light">
            <tr className="sticky-top">
            <th>OrderId</th>
            <th>Customer Name</th>
            <th>Customer Phone No</th>
            <th>Delivery Address</th>
            <th>FoodItems</th>
            <th>Unit Price</th>
            <th>Quantity</th>
            <th>Total Price</th>
            <th>Payment Status</th>
            <th>Order Status</th>
            </tr>
        </thead>
        <tbody>
           {orderHistoryData.length === 0 && (
                <tr>
                <td colSpan="10" className="text-center text-muted">
                    No orders found
                </td>
                </tr>
            )}

             {orderHistoryData.map(order =>
                order.orderedItem.map((item, index) => (
                <tr key={`${order.id}-${index}`}>

                    {index === 0 && (
                    <>
                        <td rowSpan={order.orderedItem.length}>{order.id}</td>
                        <td rowSpan={order.orderedItem.length}>{order.customerName}</td>
                        <td rowSpan={order.orderedItem.length}>{order.customerPhoneNo}</td>
                        <td rowSpan={order.orderedItem.length}>
                        {order.customerAddress}, {order.customerCity}, {order.customerState}, {order.customerCountry}
                        </td>
                    </>
                    )}
                    <td>{item.name}</td>
                    <td>৳{item.price}</td>
                    <td>{item.quantity}</td>
                    {index === 0 && (
                        <>
                            <td rowSpan={order.orderedItem.length}>৳{order.totalAmount}</td>
                            <td rowSpan={order.orderedItem.length} className={order.paymentStatus === "PAID" ?"text-success fw-bold": (order.paymentStatus === "FAILED" ?"text-danger fw-bold":"text-warning fw-bold")}>{order.paymentStatus}</td>
                            <td rowSpan={order.orderedItem.length} className={order.orderStatus === "DELIVERED" ? "text-success fw-bold" : (order.orderStatus === "FAILED" ?"text-danger fw-bold":"text-warning fw-bold")}>{order.orderStatus || "Under Observation"}</td>
                        </>
                    )}
                   
                </tr>
                ))
            )}
            
        </tbody>
        </table>

  </div>
    </div>
  )
}

export default OrderHistory;
// ngrok http 8080 --request-header-add "ngrok-skip-browser-warning: true"

///tmole 8080
//
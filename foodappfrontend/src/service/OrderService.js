import axios from "axios";
const DOMAIN=import.meta.env.VITE_API_BASE_URL || 'http://localhost:8080';
// const DOMAIN='';

const API_URL=DOMAIN+'/api/order';
export const createOrderWithPayment=async(token,data)=>{

     try {   
            const res = await axios.post(API_URL+"/create", data,{headers: { Authorization: `Bearer ${token}` }});
            return res;
            

            } catch (err) {
                    throw err;
                }
}

export const getOrderHistory=async(token)=>{

     try {   
            const res = await axios.get(API_URL,{ headers: { Authorization: `Bearer ${token}`}});
            return res;
            

            } catch (err) {
                    throw err;
                }
}

   
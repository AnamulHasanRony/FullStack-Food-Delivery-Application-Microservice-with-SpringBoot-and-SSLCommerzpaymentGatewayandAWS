import axios from "axios";
const DOMAIN=import.meta.env.VITE_API_BASE_URL || 'http://localhost:8080';
// const DOMAIN='';
const API_URL=DOMAIN+'/api/payment';
export const VerifyOrderPayment=async(token,tranId)=>{

     try {   
            const res = await axios.get(
            API_URL+ "/verify",
            {
                params: { tranId },
                headers: { Authorization: `Bearer ${token}` }  
            });
            return res;
            

            } catch (err) {
                    throw err;
                }
    }
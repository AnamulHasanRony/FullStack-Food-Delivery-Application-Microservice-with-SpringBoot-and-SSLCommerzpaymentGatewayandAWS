import axios from "axios";
const DOMAIN=import.meta.env.VITE_API_BASE_URL || 'http://localhost:8080';
// const DOMAIN='';
const API_URL=DOMAIN+'/api/contactUs';

export const submitToContactForm=async(data)=>{
    try {
            const response= await axios.post(API_URL,data);

            return response;

    } catch (error) {
        throw error;
        
    }
    
}
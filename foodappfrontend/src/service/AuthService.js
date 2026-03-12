import axios from "axios";
const DOMAIN=import.meta.env.VITE_API_BASE_URL || 'http://localhost:8080';
// const DOMAIN='';

const API_URL=DOMAIN+'/api/auth';
export const registerUser=async(data)=>{
    try {
            const response=await axios.post(API_URL+'/register', data);
            console.log(import.meta.env.VITE_API_BASE_URL)

            return response;

    } catch (error) {
        throw error;
        
    }
    
}

export const loginUser=async(data)=>{
    try {
            const response=await axios.post(API_URL+'/login', data);
            return response;

    } catch (error) {
        throw error;
        
    }
    
}


   

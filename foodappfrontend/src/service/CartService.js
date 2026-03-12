import axios from "axios";
const DOMAIN=import.meta.env.VITE_API_BASE_URL || 'http://localhost:8080';
// const DOMAIN='';
const API_URL=DOMAIN+'/api/cart';

export const addToCart=async(foodId, token)=>{
    try {
            const response= await axios.post(API_URL, {foodId}, {headers:{Authorization:`Bearer ${token}`}});

            return response;

    } catch (error) {
        throw error;
        
    }
    
}

export const decreaseQuantityFromCart=async(foodId, token)=>{
    try {
            const response= await axios.post(API_URL+'/remove', {foodId}, {headers:{Authorization:`Bearer ${token}`}});
            return response;

    } catch (error) {
        throw error;
        
    }
    
}

export const getCartData=async(token)=>{
    try {
        const response=await axios.get(API_URL,{headers:{Authorization:`Bearer ${token}`}});
            return response;

    } catch (error) {
        throw error;
        
    }
    
}

export const deleteFromCart=async(foodId, token)=>{
    try {
        const response=await axios.post(API_URL+"/delete",{foodId}, {headers:{Authorization:`Bearer ${token}`}});
        return response;

    } catch (error) {
        throw error;
        
    }
    
}

export const ClearAllCartItems=async(token)=>{
    try {

        const response=await axios.delete(API_URL, { headers: { Authorization: `Bearer ${token}` } });
        return response;

    } catch (error) {
        throw error;
        
    }
    
}



import { createContext, useEffect, useState } from "react";
import axios from 'axios'
import { getFoodlist } from "../service/FoodService";
import { addToCart, decreaseQuantityFromCart, deleteFromCart, getCartData } from "../service/CartService";
import { toast } from "react-toastify";

export const StoreContext=createContext(null);
export const StoreContextProvider=(props)=>{

    const [foodList,setFoodList]=useState([]);
    const [quantity, setQuantity]=useState([]);
    const [active,setActive]=useState('');
    // const [token,setToken]=useState("");
    const [token, setToken] = useState(
    () => localStorage.getItem("token") || ""
  );
    
    const increaseQuantity=async(foodId)=>{
        try {
            await addToCart(foodId, token);
            toast.success('added to cart successfully');
            setQuantity((prev)=>({...prev, [foodId]: (prev[foodId]||0)+1}));

        } catch (error) {
            toast.error('addition to cart failed, please login for adding to cart');
        }

    }
    const decreaseQuantity=async(foodId)=>{
        try {
           await decreaseQuantityFromCart(foodId, token);
            toast.success('successfully decreased quantity');
           setQuantity((prev)=>({...prev, [foodId]: (prev[foodId]>0?prev[foodId]-1:0)}));
           
        } catch (error) {
            toast.error('failed to decrease quantity');
        }
        
    }

    const removeFoodFromQuantity=async (foodId)=>{
       
        
        try {
            await deleteFromCart(foodId, token)
            toast.success('successfully removed item from cart');
             
            setQuantity((prev)=>{
            const updatedQuantity={...prev};
            delete updatedQuantity[foodId];
            return updatedQuantity; 
            })

        } catch (error) {
            toast.error('failed to  remove item from cart');
        }
    }
    
    const loadCartData=async(token)=>{
         try {
            const response=await getCartData(token);
            setQuantity(response.data.items);
        } catch (error) {
            toast.error('failed to  get item from cart');
        }

        
        
    }
        const clearCart = () => {
        setQuantity({});
        }
    const contextValue={
       foodList,
       increaseQuantity,
       decreaseQuantity,
       quantity,
       setQuantity,
       removeFoodFromQuantity,
       token,
       setToken,
       loadCartData,
       clearCart,
       active,
       setActive

    }
    useEffect(()=>{
        async function loadFoodlist(){
            const foods=await getFoodlist();
            setFoodList(foods);

            if(localStorage.getItem("token")){
              setToken(localStorage.getItem("token"));
              await loadCartData(localStorage.getItem("token"));
            }
        } 

        loadFoodlist();
    },[])
    return(
        <StoreContext.Provider value={contextValue}>
            {props.children}
        </StoreContext.Provider>
    )
}
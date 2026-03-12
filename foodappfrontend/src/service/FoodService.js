import axios from "axios";
const DOMAIN=import.meta.env.VITE_API_BASE_URL;
const API_URL=DOMAIN+'/api/foods';
export const getFoodlist=async()=>{
    try {
         const responseFoodList=await  axios.get(API_URL);

            if (Array.isArray(responseFoodList.data)) {
                return responseFoodList.data;
            }

            if (Array.isArray(responseFoodList.data.data)) {
                return responseFoodList.data.data;
            }
        //  return responseFoodList.data;

    } catch (error) {
        console.log("error", error);
        throw error;
    }
    }

  export  const getFoodData=async(id)=>{
    
       try {
        const responseFood=await axios.get(API_URL + "/" +id);
        if(responseFood.status==200){
           return responseFood.data;
        }
       } catch (error) {
        console.log('error while getting food data',error);
        throw error;
       }
        

    }    
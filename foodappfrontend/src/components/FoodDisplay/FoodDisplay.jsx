import React, { useContext } from 'react'
import { StoreContext } from '../../context/StoreContex'
import FoodItem from '../FoodItem/FoodItem';


const FoodDisplay = ({category,searchText}) => {
  const {foodList}=useContext(StoreContext);
   const filteredFoodList=foodList.filter(
    (food=>(category==='All' || food.category ===category) &&
    food.name.toLowerCase().includes(searchText.toLowerCase())));

  return (
    <div className="container">
      <div className="row">
        {filteredFoodList.length > 0? 
        (
          filteredFoodList.map((item,index)=>(
            <FoodItem key={index} id={item.id} name={item.name} price={item.price} description={item.description} category={item.category} image={item.imageUrl}/>
          ))
        ):(
          <div className="text-center mt-4">
          <h4>No Food Found</h4>
          </div>
        )
        }
      </div>
    </div>
  )
}

export default FoodDisplay

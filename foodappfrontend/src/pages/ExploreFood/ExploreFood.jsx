import React, { useContext, useState } from 'react'
import FoodDisplay from '../../components/FoodDisplay/FoodDisplay'
import { StoreContext } from '../../context/StoreContex';

const ExploreFood = () => {
  const {setActive}=useContext(StoreContext);
      setActive('explore');
  const [category,setCategory]=useState('All');
  const [searchText,setSearchText]=useState('');
  return (
    <>
      <div className="container">
        <div className="row justify-content-center">
          <div className="col-md-6">
            <form onSubmit={(e)=>e.preventDefault()}>
              <div className="input-group mb-3">
                <select className="form-select mt-2" style={{'maxWidth': '150px'}} onChange={(e)=>setCategory(e.target.value)}>
                   <option value="All">All</option>
                  <option value="Fast Food">Fast Food</option>
                  <option value="Desserts"> Desserts</option>
                  <option value="Local Classics">Local Classics</option>
                  <option value="International Dishes">International Dishes</option>
                  <option value="Specialty Diets">Specialty Diets</option>
                  <option value="Coffee and Tea">Coffee and Tea</option>
                  <option value="Beverages">Beverages</option>
                 </select>
                 <input type="text" className='form-control mt-2' placeholder='Search your favorite dish..' onChange={(e)=>setSearchText(e.target.value)} value={searchText}></input>
                 <button className='btn btn-primary mt-2' type="submit">
                   <i className='bi bi-search'></i>
                 </button>

              </div>

            </form>
          </div>
        </div>
      </div>
      <FoodDisplay category={category} searchText={searchText}/>
    </>
  )
}

export default ExploreFood

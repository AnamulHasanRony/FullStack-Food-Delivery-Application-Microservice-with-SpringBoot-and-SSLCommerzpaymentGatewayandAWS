import React, { useContext, useState } from 'react'
import Header from '../../components/Header/Header'
import ExploreMenu from '../../components/ExploreMenu/ExploreMenu'
import FoodDisplay from '../../components/FoodDisplay/FoodDisplay'
import { StoreContext } from '../../context/StoreContex'

const Home = () => {
  const [category,setCategory]=useState('All');
  const {setActive}=useContext(StoreContext);
      setActive('home');

  return (
    <main className='container'>
        <Header/>
        <ExploreMenu category={category} setCategory={setCategory}/>
        <FoodDisplay category={category} searchText={''}/>
    </main>
    
  )
}

export default Home

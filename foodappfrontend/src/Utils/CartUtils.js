export const calculateCartItems=(cartItems, quantity)=>{

    const subTotal= cartItems.reduce((acc,food)=> acc + food.price* quantity[food.id],0);
    const deliveryCharge= subTotal===0?0.0 : 50;
    const tax= subTotal * 0.1;
    const total=subTotal + deliveryCharge + tax;

    return {subTotal, deliveryCharge, tax, total};

}
package com.anamul.cart_service.service;

import com.anamul.cart_service.io.CartResponse;

public interface CartService {
   CartResponse addToCart(String foodId);
   CartResponse getCart();
   void clearCart();
   CartResponse removeFromCart(String foodId);

   CartResponse deleteFromCart(String foodId);
}

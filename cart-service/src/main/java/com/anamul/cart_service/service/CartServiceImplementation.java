package com.anamul.cart_service.service;

import com.anamul.cart_service.FeingRequestIntercepter.CurrentUser;
import com.anamul.cart_service.entity.CartEntity;
import com.anamul.cart_service.feignClient.UserServiceClient;
import com.anamul.cart_service.io.CartResponse;
import com.anamul.cart_service.repository.CartRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
@AllArgsConstructor
public class CartServiceImplementation implements CartService {
    private final CartRepository cartRepository;
    private final UserServiceClient userService;
    private final CurrentUser currentUser;

    @Override
    public CartResponse addToCart(String foodId) {
        String loggedInUserId=userService.getUserIdFromEmail(currentUser.getUserEmail());
        Optional<CartEntity> cartEntityOptional=cartRepository.findByUserId(loggedInUserId);
        CartEntity cartEntity=cartEntityOptional.orElseGet(()->new CartEntity(loggedInUserId, new HashMap<>()));
        Map<String, Integer> cartItems= cartEntity.getItems();
        cartItems.put(foodId,cartItems.getOrDefault(foodId,0)+1);
        cartEntity.setItems(cartItems);
        cartEntity=cartRepository.save(cartEntity);

        return convertToCartResponse(cartEntity);
    }

    @Override
    public CartResponse getCart() {
        System.out.println("current user ---> " + currentUser.getUserEmail());
        String loggedInUserId=userService.getUserIdFromEmail(currentUser.getUserEmail());
        Optional<CartEntity> cartEntityOptional = cartRepository.findByUserId(loggedInUserId);
        CartEntity cartEntity=cartEntityOptional.orElseGet(()->new CartEntity(loggedInUserId, new HashMap<>()));

        return convertToCartResponse(cartEntity);

    }

    @Override
    public void clearCart() {
        String loggedInUserId=userService.getUserIdFromEmail(currentUser.getUserEmail());
        System.out.println("loogggedinUserId for cart removing -> " + loggedInUserId);
        CartEntity cartEntity=cartRepository.findByUserId(loggedInUserId).orElse(new CartEntity(loggedInUserId, new HashMap<>()));;
        cartEntity.setItems(new HashMap<>());
        cartRepository.save(cartEntity);


    }

    @Override
    public CartResponse removeFromCart(String foodId) {
        String loggedInUserId=userService.getUserIdFromEmail(currentUser.getUserEmail());
        System.out.println("loogggedinUserId for cart removing -> " + loggedInUserId);
        Optional<CartEntity> cartEntityOptional=cartRepository.findByUserId(loggedInUserId);
        CartEntity cartEntity=cartEntityOptional.orElseThrow(()->new RuntimeException("Cart is not found"));
        Map<String, Integer> cartItems= cartEntity.getItems();
        if(cartItems.containsKey(foodId)){
            int foodQuantity=cartItems.get(foodId);
            if(foodQuantity>0){
                cartItems.put(foodId, foodQuantity-1);
            }else{
                cartItems.remove(foodId);
            }
            cartEntity.setItems(cartItems);
            cartRepository.save(cartEntity);

        }
        cartEntity.setItems(cartItems);
        cartEntity=cartRepository.save(cartEntity);
        return convertToCartResponse(cartEntity);
    }

    @Override
    public CartResponse deleteFromCart(String foodId) {
        String loggedInUserId=userService.getUserIdFromEmail(currentUser.getUserEmail());
        Optional<CartEntity> cartEntityOptional=cartRepository.findByUserId(loggedInUserId);
        CartEntity cartEntity=cartEntityOptional.orElseThrow(()->new RuntimeException("Cart is not found"));
        Map<String, Integer> cartItems= cartEntity.getItems();
        if(cartItems.containsKey(foodId)){
            cartItems.remove(foodId);
            cartEntity.setItems(cartItems);
            cartEntity=cartRepository.save(cartEntity);

        }

        return convertToCartResponse(cartEntity);
    }

    private CartResponse convertToCartResponse(CartEntity cartEntity) {
        return CartResponse.builder()
                .id(cartEntity.getId())
                .items(cartEntity.getItems())
                .userId(cartEntity.getUserId())
                .build();
    }
}

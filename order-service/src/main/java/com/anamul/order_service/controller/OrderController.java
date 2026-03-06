package com.anamul.order_service.controller;

import com.anamul.order_service.FeingRequestIntercepter.CurrentUser;
import com.anamul.order_service.entity.OrderEntity;
import com.anamul.order_service.feignClient.CartServiceClient;
import com.anamul.order_service.feignClient.FoodServiceClient;
import com.anamul.order_service.feignClient.UserServiceClient;
import com.anamul.order_service.io.*;
import com.anamul.order_service.service.OrderService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/api/order")
@AllArgsConstructor
public class OrderController {

    private final OrderService orderService;
    private final CartServiceClient cartService;
    private final FoodServiceClient foodService;
    private final UserServiceClient userService;
    private final CurrentUser currentUser;


    @GetMapping
    public List<OrderResponse> getAllOrderedResponse(){
      return orderService.getAllOrderedResponse();

    }

    @GetMapping("/all")
    public List<OrderResponse> getOrderedResponseOfAllUser(){
        return orderService.getAllOrderedResponseOfAllUser();

    }
    @PostMapping("/update/{orderId}")
    public ResponseEntity<Void> updateOrderStatus(@RequestParam String status, @PathVariable String orderId){
        orderService.updateOrderStatus(orderId,status);
            return ResponseEntity.ok().build();
    }

    @PostMapping("/create")
    public ResponseEntity<Map<String,String>> createOrderWithPayment(@RequestBody OrderRequest orderRequest){

        CartResponse cartResponse=cartService.getCart();
        if(cartResponse==null || cartResponse.getItems().isEmpty()){
            return ResponseEntity.badRequest().body(Map.of("error","Cart is empty"));
        }

         orderRequest = createOrderRequestWithPartialOrderRequestObject(orderRequest,cartResponse);

         String paymentUrl= orderService.createOrderWithPayment(orderRequest);
        return ResponseEntity.ok(Map.of("paymentUrl", paymentUrl));


    }

    private OrderRequest createOrderRequestWithPartialOrderRequestObject(OrderRequest orderRequest, CartResponse cartResponse) {


        List<OrderItem> orderItems = cartResponse.getItems().entrySet().stream().map(entry -> {
            String foodId = entry.getKey();
            int quantity = entry.getValue();
            FoodResponse foodResponse = foodService.getFoodById(foodId);

            double price=foodResponse.getPrice();
            String description=foodResponse.getDescription();
            String imageUrl=foodResponse.getImageUrl();
            String name=foodResponse.getName();
            String category=foodResponse.getCategory();
            return new OrderItem(foodId, quantity, price, description, imageUrl, name, category);
        }).toList();

        orderRequest.setOrderedItem(orderItems);
        orderRequest.setPaymentStatus("FAILED");
        orderRequest.setOrderStatus("FAILED");
        double subTotal = orderItems.stream().mapToDouble(i -> i.getPrice() * i.getQuantity()).sum();
        double totalAmount=subTotal+subTotal*0.1+50.0;
        orderRequest.setTotalAmount(totalAmount);
        orderRequest.setCustomerEmail(currentUser.getUserEmail());
        orderRequest.setUserId(userService.getUserIdFromEmail(currentUser.getUserEmail()));
        return orderRequest;
    }

    @PutMapping("/updatePaymentStatus")
    public ResponseEntity<String> updatePaymentStatus(
            @RequestParam String tranId,
            @RequestParam String status) {
        orderService.updatePaymentStatus(tranId,status);
        return ResponseEntity.ok("ok");

    }
    @GetMapping("/{orderId}")
    public OrderResponse getOrderById(@PathVariable String orderId) {
        return orderService.getOrderById(orderId);
    }


}

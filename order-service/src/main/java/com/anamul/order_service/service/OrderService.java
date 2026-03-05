package com.anamul.order_service.service;

import com.anamul.order_service.io.OrderRequest;
import com.anamul.order_service.io.OrderResponse;

import java.util.List;

public interface OrderService {

     String createOrderWithPayment(OrderRequest orderRequest);

     void updatePaymentStatus(String orderId, String paid);

     OrderResponse getOrderById(String orderId);

     List<OrderResponse> getAllOrderedResponse();

     List<OrderResponse> getAllOrderedResponseOfAllUser();

     void updateOrderStatus(String orderId, String status);
}

package com.anamul.payment_service.service;


import com.anamul.payment_service.io.OrderEntity;

public interface PaymentService {
    String initiatePayment(OrderEntity orderEntity);

    boolean verifyPayment(String tranId, String valId);
}

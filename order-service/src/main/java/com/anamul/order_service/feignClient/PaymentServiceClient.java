package com.anamul.order_service.feignClient;

import com.anamul.order_service.entity.OrderEntity;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "payment-service")
public interface PaymentServiceClient {
    @PostMapping("/api/payment")
    public String initiatePayment(@RequestBody OrderEntity orderEntity);

    }

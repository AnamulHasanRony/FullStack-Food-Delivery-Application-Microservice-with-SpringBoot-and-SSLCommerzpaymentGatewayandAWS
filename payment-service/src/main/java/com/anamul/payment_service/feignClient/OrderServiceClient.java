package com.anamul.payment_service.feignClient;

import com.anamul.payment_service.io.OrderResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "order-service")
public interface OrderServiceClient {
    @PutMapping("/api/order/updatePaymentStatus")
    public ResponseEntity<String> updatePaymentStatus(
            @RequestParam String tranId,
            @RequestParam String status);

    @GetMapping("/api/order/{orderId}")
    public OrderResponse getOrderById(@PathVariable String orderId);
}

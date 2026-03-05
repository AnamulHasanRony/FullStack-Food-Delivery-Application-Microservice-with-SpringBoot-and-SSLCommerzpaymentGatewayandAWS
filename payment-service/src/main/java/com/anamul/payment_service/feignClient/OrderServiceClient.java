package com.anamul.payment_service.feignClient;

import com.anamul.payment_service.io.OrderResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "order-service")
public interface OrderServiceClient {
    @PatchMapping("/updatePaymentStatus")
    public ResponseEntity<String> updatePaymentStatus(
            @RequestParam String tranId,
            @RequestParam String status);

    @GetMapping("/{orderId}")
    public OrderResponse getOrderById(@PathVariable String orderId);
}

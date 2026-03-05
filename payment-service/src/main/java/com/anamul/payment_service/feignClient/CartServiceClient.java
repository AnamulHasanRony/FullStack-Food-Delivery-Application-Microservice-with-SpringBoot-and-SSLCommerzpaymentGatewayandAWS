package com.anamul.payment_service.feignClient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "cart-service")
public interface CartServiceClient {
//    @GetMapping("/api/cart")
//    public CartResponse getCart();
}

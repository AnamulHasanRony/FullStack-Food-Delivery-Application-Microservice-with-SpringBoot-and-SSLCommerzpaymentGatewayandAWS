package com.anamul.order_service.feignClient;

import com.anamul.order_service.io.CartResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "cart-service")
public interface CartServiceClient {
    @GetMapping("/api/cart")
    public CartResponse getCart();
}

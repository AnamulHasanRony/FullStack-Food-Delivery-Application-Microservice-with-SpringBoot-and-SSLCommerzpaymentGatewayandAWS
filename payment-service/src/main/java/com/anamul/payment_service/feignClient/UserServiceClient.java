package com.anamul.payment_service.feignClient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name="AUTHENTICATION-SERVICE")
public interface UserServiceClient {
    @GetMapping("/api/auth/userid/{email}")
    String getUserIdFromEmail(@PathVariable String email);
}

package com.anamul.payment_service.feignClient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "food-service")
public interface FoodServiceClient {
//    @GetMapping("/api/foods/{id}")
//    public FoodResponse getFoodById(@PathVariable String id);

}

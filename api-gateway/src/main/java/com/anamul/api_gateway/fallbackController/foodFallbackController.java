package com.anamul.api_gateway.fallbackController;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class foodFallbackController {
//    @GetMapping("/fallback/foodService")
    public String foodServiceFallback(){
        return "Food Service Unavailable";

    }
}

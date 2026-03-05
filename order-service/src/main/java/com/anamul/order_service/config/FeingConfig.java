package com.anamul.order_service.config;

import com.anamul.order_service.FeingRequestIntercepter.ForwardUserHeadersInterceptor;
import feign.RequestInterceptor;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

@Configuration
public class FeingConfig {
    @Bean
    @Scope("prototype")
    public RequestInterceptor userHeadersInterceptor(HttpServletRequest request) {
        return new ForwardUserHeadersInterceptor(request);
    }
}

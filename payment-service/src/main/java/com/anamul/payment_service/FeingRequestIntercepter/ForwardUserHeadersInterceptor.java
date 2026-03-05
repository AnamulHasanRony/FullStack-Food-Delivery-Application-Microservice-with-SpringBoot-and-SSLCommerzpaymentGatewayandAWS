package com.anamul.payment_service.FeingRequestIntercepter;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class ForwardUserHeadersInterceptor implements RequestInterceptor {

    private final HttpServletRequest request;

    @Autowired
    public ForwardUserHeadersInterceptor(HttpServletRequest request) {
        this.request = request;
    }

    @Override
    public void apply(RequestTemplate template) {
        String email = request.getHeader("X-User-Email");
        if (email != null) template.header("X-User-Email", email);

        String role = request.getHeader("X-Role");
        if (role != null) template.header("X-Role", role);
    }
}

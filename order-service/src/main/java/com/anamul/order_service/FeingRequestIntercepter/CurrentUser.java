package com.anamul.order_service.FeingRequestIntercepter;


import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

@Component
@RequestScope
public class CurrentUser {

    private final HttpServletRequest request;

    @Autowired
    public CurrentUser(HttpServletRequest request) {
        this.request = request;
    }

    public String getUserEmail() {
        return request.getHeader("X-User-Email");
    }

    public boolean hasRole(String role) {
        String roles = request.getHeader("X-Role");
        return roles != null && roles.contains(role);
    }
}
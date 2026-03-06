package com.anamul.api_gateway.filter;

import com.anamul.api_gateway.Utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;
import org.springframework.http.HttpMethod;


@Component
public class JwtGlobalFilter implements GlobalFilter, Ordered {

    @Autowired
    private JwtUtil jwtUtil;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {


        // Add CORS headers to all responses
//        exchange.getResponse().getHeaders().add("Access-Control-Allow-Origin", "http://localhost:5173");
//        exchange.getResponse().getHeaders().add("Access-Control-Allow-Methods", "GET,POST,PUT,DELETE,OPTIONS,PATCH");
//        exchange.getResponse().getHeaders().add("Access-Control-Allow-Headers", "Authorization,Content-Type,X-User-Email,X-Role");
//        exchange.getResponse().getHeaders().add("Access-Control-Allow-Credentials", "true");
//        exchange.getResponse().getHeaders().add("Access-Control-Max-Age", "3600");
//
//        ServerHttpRequest request = exchange.getRequest();
//        String path = request.getURI().getPath();
//
//        if (request.getMethod() == HttpMethod.OPTIONS) {
//            exchange.getResponse().setStatusCode(HttpStatus.OK);
//            return exchange.getResponse().setComplete();
//        }

        ServerHttpRequest request = exchange.getRequest();

        if (request.getMethod() == HttpMethod.OPTIONS) {
            return chain.filter(exchange);
        }


        String path = request.getURI().getPath();
///
///                 path.startsWith("/api/foods/")
        System.out.println("in request---> " + path);

        // Skip public paths
        if (path.startsWith("/api/auth") ||
                path.startsWith("/api/contactUs") || path.startsWith("/api/payment/") || path.startsWith("/api/foods") ) {
            return chain.filter(exchange);
        }

        String authHeader = request.getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
            return exchange.getResponse().setComplete();
        }

        String token = authHeader.substring(7);

        if (!jwtUtil.isTokenValid(token)) {
            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
            return exchange.getResponse().setComplete();
        }

        String email = jwtUtil.extractUsername(token);
        String role  = jwtUtil.extractRole(token);

        ServerHttpRequest mutated = request.mutate()
                .header("X-User-Email", email)
                .header("X-Role", role)
                .build();
        System.out.println("X-User-Email" + email);
        System.out.println("X-Role" + role);

        return chain.filter(exchange.mutate().request(mutated).build());
    }

    @Override
    public int getOrder() {
        return -100; // early
    }
}
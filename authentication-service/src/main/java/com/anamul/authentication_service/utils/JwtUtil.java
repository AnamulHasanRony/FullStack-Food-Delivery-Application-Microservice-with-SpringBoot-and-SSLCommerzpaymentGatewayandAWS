package com.anamul.authentication_service.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


@Component
public class JwtUtil {
    @Value("${jwt.secret.key}")
    private String SECRET_KEY;

    public String generateToken(UserDetails userDetails, String role){
        Map<String,Object> claims=new HashMap<>();
        claims.put("role", role);
        return createToken(claims, userDetails.getUsername());
        
    }

    private String createToken(Map<String, Object> claims, String username) {
        return Jwts.builder()
                .claims(claims)
                .subject(username)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60* 4))
                .signWith(getSigningKey()) // SecretKey
                .compact();

    }

    private SecretKey getSigningKey() {
        return Keys.hmacShaKeyFor(SECRET_KEY.getBytes());
    }


    public String extractUsername(String token) {
        return extractAllClaims(token).getSubject();
    }
    public String extractRole(String token){
        return extractAllClaims(token).get("role", String.class);
    }

    public Date extractExpiration(String token) {
        return extractAllClaims(token).getExpiration();
    }

    public boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return username.equals(userDetails.getUsername())
                && !isTokenExpired(token);
    }

    private Claims extractAllClaims(String token) {
        return Jwts
                .parser()
                .verifyWith(getSigningKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }



}

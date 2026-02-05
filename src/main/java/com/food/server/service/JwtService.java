package com.food.server.service;

import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.stereotype.Service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtService {
    
    private static final String SECRET = "my_super_secret_key_for_jwt_signing_1234567890";
    
    // ✅ Cache the secret key to avoid regenerating it on every token operation
    private static final SecretKey KEY = Keys.hmacShaKeyFor(SECRET.getBytes());
    
    private long expiration = 86400000; // 1 day in milliseconds

    private SecretKey getKey(){
        return KEY;
    }
    
    public String generateToken(String email) {
        return Jwts.builder()
                .setSubject(email)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(getKey())
                .compact();
    }

    public String extractEmail(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getKey())
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }
}

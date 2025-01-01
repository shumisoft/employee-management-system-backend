package com.shumisoft.employee_management_system.utils;

import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.shumisoft.employee_management_system.dto.response.TokenResponseDTO;
import com.shumisoft.employee_management_system.entity.User;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtUtil {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiry-ms}")
    private Long expirationMs;

    @Value("${jwt.refresh-expiry-ms}")
    private Long refreshExpirationMs;

    public TokenResponseDTO generateTokens(User user) {

        String token = this.generateToken(user, expirationMs);
        String refreshToken = this.generateToken(user, refreshExpirationMs);

        return TokenResponseDTO.builder().token(token).refreshToken(refreshToken).build();

    }

    public Claims extractClaims(String token) {

        return Jwts.parser().verifyWith(this.getSecretKey()).build().parseSignedClaims(token).getPayload();

    }

    public boolean isTokenValid(String token) {

        try {

            this.extractClaims(token);

            return true;

        } catch (Exception e) {

            return false;

        }

    }

    public String extractUsername(String token) {

        return this.extractClaims(token).getSubject();

    }

    private String generateToken(User user, Long expirationMs) {

        return Jwts.builder().subject(user.getUsername())
                .claim("role", user.getRole().toString())
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + expirationMs))
                .signWith(this.getSecretKey())
                .compact();

    }

    private SecretKey getSecretKey() {

        return Keys.hmacShaKeyFor(secret.getBytes());

    }

}

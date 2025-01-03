package com.shumisoft.employee_management_system.config;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

import com.shumisoft.employee_management_system.utils.JwtUtil;

import io.jsonwebtoken.Claims;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class JwtAuthenticationProvider implements AuthenticationProvider {

    JwtUtil jwtUtil;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        String token = (String) authentication.getCredentials();

        if (jwtUtil.isTokenValid(token)) {

            Claims claims = jwtUtil.extractClaims(token);
            String role = (String) claims.get("role");
            String username = claims.getSubject();

            return new JwtAuthenticationToken(username, role);

        }

        return null;

    }

    @Override
    public boolean supports(Class<?> authentication) {

        return JwtAuthenticationToken.class.isAssignableFrom(authentication);
    }

}

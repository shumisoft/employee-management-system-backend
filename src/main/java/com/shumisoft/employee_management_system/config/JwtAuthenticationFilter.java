package com.shumisoft.employee_management_system.config;

import java.io.IOException;

import org.springframework.lang.NonNull;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@AllArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final AuthenticationManager authenticationManager;

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain)
            throws ServletException, IOException {

        var context = SecurityContextHolder.getContext();

        if (context.getAuthentication() != null && context.getAuthentication().isAuthenticated()) {

            filterChain.doFilter(request, response);
        }

        String authHeader = request.getHeader("Authorization");

        log.info(authHeader);

        if (authHeader != null && authHeader.startsWith("Bearer ")) {

            String token = authHeader.substring(7);

            Authentication unauthorizedToken = new JwtAuthenticationToken(token);

            Authentication autorizedToken = authenticationManager.authenticate(unauthorizedToken);

            if (autorizedToken != null && autorizedToken.isAuthenticated()) {

                var newContext = SecurityContextHolder.createEmptyContext();

                newContext.setAuthentication(autorizedToken);

                SecurityContextHolder.setContext(newContext);
            }

        }

        filterChain.doFilter(request, response);

    }

}

package com.shumisoft.employee_management_system.config;

import java.io.IOException;

import org.springframework.lang.NonNull;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class IPLoggerFilter extends OncePerRequestFilter {

        @Override
        protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response,
                        @NonNull FilterChain filterChain)
                        throws ServletException, IOException {

                String userAgent = request.getHeader("User-Agent");
                userAgent = (userAgent != null) ? userAgent : "Unknown User-Agent";

                log.info(
                                """
                                                Request Audit:
                                                        method       : {}
                                                        endpoint     : {}
                                                        remote IP    : {}
                                                        user agent   : {}
                                                        query string : {}""",
                                request.getMethod(),
                                request.getRequestURI(),
                                request.getRemoteAddr(),
                                userAgent,
                                request.getQueryString());

                filterChain.doFilter(request, response);
        }

}

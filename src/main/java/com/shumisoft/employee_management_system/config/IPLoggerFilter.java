package com.shumisoft.employee_management_system.config;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.lang.NonNull;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class IPLoggerFilter extends OncePerRequestFilter {

        private static final Logger ipLogger = LoggerFactory.getLogger(IPLoggerFilter.class);

        @Override
        protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response,
                        @NonNull FilterChain filterChain)
                        throws ServletException, IOException {

                String userAgent = request.getHeader("User-Agent");
                userAgent = (userAgent != null) ? userAgent : "Unknown User-Agent";

                ipLogger.info(
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

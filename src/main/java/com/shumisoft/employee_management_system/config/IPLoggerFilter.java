package com.shumisoft.employee_management_system.config;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class IPLoggerFilter extends OncePerRequestFilter {

    Logger logger = LoggerFactory.getLogger(IPLoggerFilter.class);

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        logger.info(
                "Request Audit:\n"
                        + "        method       : {}\n"
                        + "        endpoint     : {}\n"
                        + "        remote IP    : {}\n"
                        + "        user agent   : {}\n"
                        + "        query string : {}\n"
                        + "        session ID   : {}",
                request.getMethod(),
                request.getRequestURI(),
                request.getRemoteAddr(),
                request.getHeader("User-Agent"),
                request.getQueryString(),
                request.getRequestedSessionId());

        filterChain.doFilter(request, response);
    }

}

package com.e_commerce.shop_service.security;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class CredentialsCaptureFilter implements Filter {
    public static ThreadLocal<String> authHeader = new ThreadLocal<>();

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        String authorization = httpServletRequest.getHeader("Authorization");
        if (authorization != null && authorization.toLowerCase().startsWith("basic")) {
            authHeader.set(authorization);
        }

        try {
            chain.doFilter(request, response);
        } finally {
            authHeader.remove();
        }
    }
}

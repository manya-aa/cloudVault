package com.project.cloudInventory.configuration;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class ApiKeyAuthFilter extends OncePerRequestFilter {

    private final String API_KEY = System.getenv("API_KEY");

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {

        String path = request.getRequestURI();

        // 1. Let health check pass freely
        if (path.equals("/actuator/health")) {
            filterChain.doFilter(request, response);
            return;
        }

        // 2. Only protect /api endpoints
        if (!path.startsWith("/api/")) {
            filterChain.doFilter(request, response);
            return;
        }

        // 3. Read API key from request header
        String requestKey = request.getHeader("X-API-KEY");

        // 4. Validate key
        if (requestKey == null || !requestKey.equals(API_KEY)) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("Invalid or missing API Key");
            return;
        }

        // 5. If valid → continue to controller
        filterChain.doFilter(request, response);
    }
}
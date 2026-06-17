package com.project.cloudInventory.configuration;

import com.project.cloudInventory.configuration.ApiKeyAuthFilter;
import com.project.cloudInventory.configuration.RateLimitingFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final RateLimitingFilter rateLimitingFilter;
    private final ApiKeyAuthFilter apiKeyFilter;

    public SecurityConfig(RateLimitingFilter rateLimitingFilter,
                          ApiKeyAuthFilter apiKeyFilter) {
        this.rateLimitingFilter = rateLimitingFilter;
        this.apiKeyFilter = apiKeyFilter;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http
                // disable session + CSRF for REST APIs
                .csrf(csrf -> csrf.disable())

                // authorization rules
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/actuator/health").permitAll()
                        .requestMatchers("/actuator/**").permitAll()
                        .anyRequest().authenticated()
                )

                // 1️⃣ Rate limiting runs FIRST (before authentication)
                .addFilterBefore(
                        rateLimitingFilter,
                        UsernamePasswordAuthenticationFilter.class
                )

                // 2️⃣ API key validation AFTER rate limiting
                .addFilterAfter(
                        apiKeyFilter,
                        RateLimitingFilter.class
                );

        return http.build();
    }
}
package com.idn.backend.config;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
public class CorsConfig {

	@Bean
	public CorsConfigurationSource corsConfigurationSource() {

    CorsConfiguration config = new CorsConfiguration();

    // 🔹 Which frontend is allowed to call this backend
    // WHY: Browser blocks cross-origin calls unless explicitly allowed
    config.setAllowedOrigins(List.of("http://localhost:5173"));

    // 🔹 Which HTTP methods the frontend can use
    // WHY: Browser preflight (OPTIONS) checks this before real request
    // Needed for REST APIs (GET fetch, POST create, PUT update, DELETE remove, PATCH partial update)
    config.setAllowedMethods(List.of(
        "GET", "POST", "PUT", "DELETE", "PATCH", "OPTIONS"
    ));

    // 🔹 Which request headers frontend is allowed to send
    // WHY: JWT is sent via Authorization header
    // Content-Type needed for JSON requests
    config.setAllowedHeaders(List.of(
        "Authorization",
        "Content-Type"
    ));

    // 🔹 Which response headers browser is allowed to read
    // WHY: If backend sends JWT in Authorization header,
    // browser won't expose it unless listed here
    config.setExposedHeaders(List.of("Authorization"));

    // 🔹 Allow cookies / Authorization header
    // WHY: Required for sending JWT tokens
    // Without this, browser blocks Authorization header
    config.setAllowCredentials(true);

    // 🔹 Cache preflight response for 1 hour
    // WHY: Prevents browser from sending OPTIONS request every time
    // Improves performance
    config.setMaxAge(3600L);

    UrlBasedCorsConfigurationSource source =
        new UrlBasedCorsConfigurationSource();

    // 🔹 Apply this CORS policy to all API endpoints
    // WHY: JWT-secured APIs exist under multiple routes
    source.registerCorsConfiguration("/**", config);

    return source;
}


}

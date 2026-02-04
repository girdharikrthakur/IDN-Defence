package com.idn.backend.Security;

import java.io.IOException;
import java.time.Instant;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.idn.backend.ExceptionHandler.ApiError;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class CustomSecurityHandler implements AuthenticationEntryPoint, AccessDeniedHandler {

    private final ObjectMapper objectMapper;

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response,
            AccessDeniedException accessDeniedException) throws IOException, ServletException {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED); // 401
        response.setContentType("application/json");

        objectMapper.writeValue(response.getOutputStream(),
                new ApiError(
                        Instant.now(),
                        401,
                        "Unauthorized",
                        "Authentication required to access this resource",
                        request.getRequestURI()));
    }

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
            AuthenticationException authException) throws IOException, ServletException {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED); // 401
        response.setContentType("application/json");
        objectMapper.writeValue(response.getOutputStream(),
                new ApiError(
                        Instant.now(),
                        403,
                        "Forbidden",
                        "You do not have permission to access this resource",
                        request.getRequestURI()));
    }

}

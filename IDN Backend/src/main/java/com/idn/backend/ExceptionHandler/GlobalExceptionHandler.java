package com.idn.backend.ExceptionHandler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import jakarta.servlet.http.HttpServletRequest;

import java.time.Instant;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiError> handleException(Exception ex, HttpServletRequest request) {
        ApiError apiError = new ApiError(
                Instant.now(),
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "Internal Server Error",
                ex.getMessage(),
                request.getRequestURI());
        return new ResponseEntity<>(apiError, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<ApiError> handleUserExists(UserAlreadyExistsException ex, HttpServletRequest request) {
        ApiError apiError = new ApiError(
                Instant.now(),
                HttpStatus.CONFLICT.value(),
                "Conflict",
                ex.getMessage(),
                request.getRequestURI());
        return new ResponseEntity<>(apiError, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<ApiError> userNotFound(UsernameNotFoundException ex, HttpServletRequest request) {
        ApiError apiError = new ApiError(
                Instant.now(),
                HttpStatus.NOT_FOUND.value(),
                "Not Found",
                ex.getMessage(),
                request.getRequestURI());

        return new ResponseEntity<>(apiError, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(PostNotFoundException.class)
    public ResponseEntity<ApiError> postNotFound(PostNotFoundException ex, HttpServletRequest request) {
        ApiError apiError = new ApiError(
                Instant.now(),
                HttpStatus.NOT_FOUND.value(),
                "Not Found",
                ex.getMessage(),
                request.getRequestURI());
        return new ResponseEntity<>(apiError, HttpStatus.NOT_FOUND);
    }
}

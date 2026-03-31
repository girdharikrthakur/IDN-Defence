package com.idn.backend.dto.request;

public record UserLoginRequestDTO(
        String email,
        String password) {
}

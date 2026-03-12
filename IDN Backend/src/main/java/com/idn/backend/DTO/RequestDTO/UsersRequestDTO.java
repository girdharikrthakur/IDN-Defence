package com.idn.backend.DTO.RequestDTO;

public record UsersRequestDTO(
                String userName,
                String email,
                String password,
                String role) {
}
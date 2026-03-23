package com.idn.backend.dto;

import com.idn.backend.entity.Role;

public record UserDTO(
        Long id,
        String userName,
        String email,
        Role role) {
}

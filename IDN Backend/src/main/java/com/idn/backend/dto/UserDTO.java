package com.idn.backend.dto;

import com.idn.backend.entity.Role;

// This is for Dashboard
public record UserDTO(
                Long id,
                String userName,
                String email,
                Role role,
                Long postCount) {
}

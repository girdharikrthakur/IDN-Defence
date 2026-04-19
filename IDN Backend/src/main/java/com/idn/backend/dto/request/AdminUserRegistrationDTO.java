package com.idn.backend.dto.request;

import com.idn.backend.entity.Role;

public record AdminUserRegistrationDTO(
        String userName,
        Role role,
        String email,
        String password) {

}

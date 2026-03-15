package com.idn.backend.DTO.RequestDTO;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record UsersRequestDTO(

        @NotBlank(message = "Username cannot be empty")
        String userName,

        @Email(message = "Invalid email format")
        @NotBlank(message = "Email cannot be empty")
        String email,

        @NotBlank(message = "Password cannot be empty")
        @Size(min = 6, max = 100, message = "Password must be at least 6 characters long")
        @Pattern(
            regexp = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]+$",
            message = "Password must contain at least one letter and one number"
        )
        String password,

        String role
) {}
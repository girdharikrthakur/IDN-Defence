package com.idn.backend.DTO;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class UsersResponseDTO {

    private String userName;

    private String email;

    private boolean emailVerified;

    private String verificationToken;

    private LocalDateTime tokenExpiry;

}

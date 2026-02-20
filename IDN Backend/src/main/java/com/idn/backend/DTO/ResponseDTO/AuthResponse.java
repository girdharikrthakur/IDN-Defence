package com.idn.backend.DTO.ResponseDTO;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class AuthResponse {

    private String message;
    private boolean success;
}

package com.idn.backend.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegisterRequest {

    private String username;
    private String password;
    private String email;

    // getters & setters
}

package com.idn.backend.DTO;

import lombok.Data;

@Data
public class AuthRequestDTO {

    private String userName;

    private String email;

    private String pwd;

}

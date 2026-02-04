package com.idn.backend.DTO;

import lombok.Data;

@Data
public class UsersRequestDTO {

    private Long id;

    private String userName;

    private String email;

    private String pwd;

    private String role;
}

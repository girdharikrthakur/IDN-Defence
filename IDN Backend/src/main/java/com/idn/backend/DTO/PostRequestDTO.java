package com.idn.backend.DTO;

import lombok.Data;

@Data
public class PostRequestDTO {

    private String title;
    private String content;
    private Long userId;
}

package com.idn.backend.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PostSearchRequest {
    private String keyword;
    private String category;
}

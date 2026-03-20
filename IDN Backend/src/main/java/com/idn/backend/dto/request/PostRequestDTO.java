package com.idn.backend.dto.request;

import java.util.List;

import com.idn.backend.entity.Draft;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PostRequestDTO {
    private String title;
    private String content;
    private Long authorId;
    private List<Long> categoryIds;
    private Draft status;
}

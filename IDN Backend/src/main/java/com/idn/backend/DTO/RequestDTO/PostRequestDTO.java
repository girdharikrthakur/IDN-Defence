package com.idn.backend.DTO.RequestDTO;

import java.util.List;

import com.idn.backend.Model.Draft;

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

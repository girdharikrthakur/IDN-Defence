package com.idn.backend.dto.response;

import java.util.List;

import com.idn.backend.entity.Draft;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PostResponseDTO {
    private Long id;

    private String title;

    private String content;

    private String authorName;

    private List<String> categories;

    private Draft status;

}

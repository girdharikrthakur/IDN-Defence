package com.idn.backend.DTO;

import java.time.LocalDateTime;

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

    private String imgUrl;

    private String category;

    private int views;

    private LocalDateTime publishedAt;

    private LocalDateTime updatedAt;

    private boolean isPublished;

}

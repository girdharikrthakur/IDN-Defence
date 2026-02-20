package com.idn.backend.DTO.ResponseDTO;

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
    private String categoryName;
    private String authorName;
    private int views;
    private LocalDateTime publishedAt;
    private LocalDateTime updatedAt;
    private boolean isPublished;
}

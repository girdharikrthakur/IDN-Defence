package com.idn.backend.DTO;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class CommentResponseDTO {

    private Long id;
    private String content;
    private String username;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Long parentCommentId;
}

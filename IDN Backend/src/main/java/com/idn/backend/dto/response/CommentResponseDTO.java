package com.idn.backend.dto.response;

import java.util.List;

import lombok.Data;

@Data
public class CommentResponseDTO {

    private Long id;
    private String content;

    private Long postId;
    private Long userId;

    private Long parentId;

    private List<CommentResponseDTO> replies;
}

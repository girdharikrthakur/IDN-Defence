package com.idn.backend.dto.request;

public record CommentRequest(
        String content,
        Long postId,
        Long parentId) {

}

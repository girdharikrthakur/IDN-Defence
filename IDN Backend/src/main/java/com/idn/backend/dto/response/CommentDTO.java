package com.idn.backend.dto.response;

import java.util.List;

public record CommentDTO(
                Long id,
                String content,
                List<CommentDTO> replies) {
}
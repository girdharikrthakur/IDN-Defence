package com.idn.backend.DTO.ResponseDTO;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class DraftResponseDTO {

    private String title;

    private String content;

    private String imageUrl;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private boolean isPublished;
}

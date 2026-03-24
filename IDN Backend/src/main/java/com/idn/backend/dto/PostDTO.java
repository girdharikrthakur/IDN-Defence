package com.idn.backend.dto;

public record PostDTO(
        Long id,
        String title,
        String authorEmail,
        Long viewsCount) {
}
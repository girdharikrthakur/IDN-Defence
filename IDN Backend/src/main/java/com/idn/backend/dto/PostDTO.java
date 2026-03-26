package com.idn.backend.dto;

// This for Dashboard
public record PostDTO(
                Long id,
                String title,
                String authorEmail,
                Long viewsCount) {
}
package com.idn.backend.dto;

// This is for Dashboard
public record CategoryStatsDTO(
        String category,
        Long postCount,
        Long totalViews) {

}

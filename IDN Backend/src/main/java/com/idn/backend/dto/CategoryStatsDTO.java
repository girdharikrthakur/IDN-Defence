package com.idn.backend.dto;

public record CategoryStatsDTO(
                String category,
                Long postCount,
                Long totalViews) {

}

package com.idn.backend.dto;

public record DashboardStatsDTO(
        Long totalPosts,
        Long totalViews,
        Long totalUsers,
        Long totalCategories) {
}

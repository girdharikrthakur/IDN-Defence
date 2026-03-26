package com.idn.backend.dto;

public record UserProfileDTO(
        Long userId,
        String username,
        long postCount) {

}

package com.idn.backend.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.idn.backend.dto.CategoryStatsDTO;
import com.idn.backend.dto.DashboardStatsDTO;
import com.idn.backend.dto.UserDTO;
import com.idn.backend.dto.request.AdminUserRegistrationDTO;
import com.idn.backend.dto.request.RegistrationDTO;
import com.idn.backend.dto.response.CursorPageResponse;
import com.idn.backend.dto.response.PostResponseDTO;
import com.idn.backend.service.impl.AdminDashboardServiceImpl;
import com.idn.backend.service.impl.PostServiceImpl;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admin")
public class AdminController {

    private final AdminDashboardServiceImpl dashboardService;
    private final PostServiceImpl postService;

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/dashboard/stats")
    public DashboardStatsDTO getStats() {
        return dashboardService.getStats();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/dashboard/category-stats")
    public ResponseEntity<List<CategoryStatsDTO>> getCategoryStats() {
        List<CategoryStatsDTO> categoryStats = dashboardService.getCategoryStats();
        return ResponseEntity.ok(categoryStats);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/dashboard/posts")
    public ResponseEntity<CursorPageResponse<PostResponseDTO>> getPosts(
            @RequestParam(required = false) Long cursor,
            @RequestParam(defaultValue = "10") int limit) {

        CursorPageResponse<PostResponseDTO> posts = postService.getPosts(cursor, limit);

        return ResponseEntity.ok(posts);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/posts/{id}")
    public void deletePost(@PathVariable Long id) {
        postService.deletePost(id);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/users")
    public List<UserDTO> getUsers() {
        return dashboardService.getUsers();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/adduser")
    public ResponseEntity<String> addUser(@RequestBody AdminUserRegistrationDTO reg, HttpServletRequest request) {

        dashboardService.addUser(reg, request);
        return ResponseEntity.ok("User added successfully");

    }

}

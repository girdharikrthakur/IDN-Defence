package com.idn.backend.controller;

import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.idn.backend.dto.CategoryStatsDTO;
import com.idn.backend.dto.DashboardStatsDTO;
import com.idn.backend.dto.PostDTO;
import com.idn.backend.dto.UserDTO;
import com.idn.backend.repo.PostRepo;
import com.idn.backend.services.impl.AdminDashboardServiceImpl;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admin")
public class AdminController {

    private final AdminDashboardServiceImpl dashboardService;
    private final PostRepo postRepository;

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/dashboard/stats")
    public DashboardStatsDTO getStats() {
        return dashboardService.getStats();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/dashboard/category-stats")
    public List<CategoryStatsDTO> getCategoryStats() {
        return dashboardService.getCategoryStats();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/posts")
    public List<PostDTO> getPosts() {
        return dashboardService.getPosts();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/posts/{id}")
    public void deletePost(@PathVariable Long id) {
        postRepository.deleteById(id);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/users")
    public List<UserDTO> getUsers() {
        return dashboardService.getUsers();
    }

}

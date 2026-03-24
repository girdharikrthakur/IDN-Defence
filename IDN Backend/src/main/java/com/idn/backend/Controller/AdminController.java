package com.idn.backend.controller;

import java.util.List;

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

    @GetMapping("/dashboard/stats")
    public DashboardStatsDTO getStats() {
        return dashboardService.getStats();
    }

    @GetMapping("/dashboard//category-stats")
    public List<CategoryStatsDTO> getCategoryStats() {
        return dashboardService.getCategoryStats();
    }

    @GetMapping("/posts")
    public List<PostDTO> getPosts() {
        return dashboardService.getPosts();
    }

    @DeleteMapping("/posts/{id}")
    public void deletePost(@PathVariable Long id) {
        postRepository.deleteById(id);
    }

    @GetMapping("/users")
    public List<UserDTO> getUsers() {
        return dashboardService.getUsers();
    }
}

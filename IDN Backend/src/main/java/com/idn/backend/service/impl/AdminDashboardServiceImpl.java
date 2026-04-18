package com.idn.backend.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.idn.backend.dto.CategoryStatsDTO;
import com.idn.backend.dto.DashboardStatsDTO;
import com.idn.backend.dto.PostDTO;
import com.idn.backend.dto.UserDTO;

import com.idn.backend.repo.AppUserRepo;
import com.idn.backend.repo.CategoryRepo;
import com.idn.backend.repo.PostRepo;

import lombok.RequiredArgsConstructor;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class AdminDashboardServiceImpl {

    private final PostRepo postRepository;

    private final AppUserRepo userRepository;

    private final CategoryRepo categoryRepository;

    public DashboardStatsDTO getStats() {
        return new DashboardStatsDTO(
                postRepository.countPosts(),
                postRepository.sumViews(),
                userRepository.countUsers(),
                categoryRepository.countCategories());
    }

    public List<CategoryStatsDTO> getCategoryStats() {
        return postRepository.getCategoryStats();
    }

    public List<PostDTO> getPosts() {
        return postRepository.getAllPosts();
    }

    public List<UserDTO> getUsers() {

        return userRepository.getAllUsers();
    }

}
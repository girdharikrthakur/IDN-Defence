package com.idn.backend.service.impl;

import java.util.List;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.idn.backend.dto.CategoryStatsDTO;
import com.idn.backend.dto.DashboardStatsDTO;
import com.idn.backend.dto.PostDTO;
import com.idn.backend.dto.UserDTO;
import com.idn.backend.dto.request.AdminUserRegistrationDTO;
import com.idn.backend.dto.request.RegistrationDTO;
import com.idn.backend.entity.AppUser;
import com.idn.backend.entity.Role;
import com.idn.backend.exception.UserAlreadyExistsException;
import com.idn.backend.repo.AppUserRepo;
import com.idn.backend.repo.CategoryRepo;
import com.idn.backend.repo.PostRepo;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class AdminDashboardServiceImpl {

    private final PostRepo postRepository;
    private final PasswordEncoder passwordEncoder;
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

    @Transactional
    public void addUser(AdminUserRegistrationDTO reg, HttpServletRequest request) {
        AppUser user = userRepository.findByEmail(reg.email())
                .orElseThrow(() -> new UserAlreadyExistsException("User Already Exists by email: " + reg.email()));

        user.setEmail(reg.email());
        user.setUserName(reg.userName());
        user.setPassword(passwordEncoder.encode(reg.password()));
        user.setEmailVerified(true);
        user.setRole(reg.role());
        user.setEnabled(true);
        user.setActive(true);
        userRepository.save(user);

    }

}
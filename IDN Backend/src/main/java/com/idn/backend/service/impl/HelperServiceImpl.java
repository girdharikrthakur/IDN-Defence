package com.idn.backend.service.impl;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.idn.backend.entity.AppUser;
import com.idn.backend.repo.AppUserRepo;
import com.idn.backend.service.HelperServices;

import lombok.RequiredArgsConstructor;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class HelperServiceImpl implements HelperServices {

    private final AppUserRepo appUserRepo;

    public AppUser getCurrentUser() {
        String email = SecurityContextHolder.getContext()
                .getAuthentication()
                .getName();

        return appUserRepo.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }
}

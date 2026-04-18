package com.idn.backend.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.idn.backend.dto.request.UserLoginRequestDTO;
import com.idn.backend.dto.request.UsersRequestDTO;
import com.idn.backend.dto.response.UsersResponseDTO;
import com.idn.backend.entity.AppUser;
import com.idn.backend.exception.UsernameNotFoundException;
import com.idn.backend.mapper.AppUserMapper;
import com.idn.backend.repo.AppUserRepo;
import com.idn.backend.service.AppUserService;

import lombok.RequiredArgsConstructor;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class AppUserServiceImpl implements AppUserService {

    private final AppUserRepo appUserRepo;
    private final AppUserMapper appUserMapper;

    @Override
    public List<UsersResponseDTO> findAllUsers() {
        List<AppUser> users = appUserRepo.findAll();
        List<UsersResponseDTO> userDTOs = appUserMapper.toAppUserResponseDTOList(users);
        return userDTOs;
    }

    @Override
    public UsersResponseDTO findUserById(Long id) {
        Optional<AppUser> userOptional = appUserRepo.findById(id);
        if (userOptional.isEmpty()) {
            throw new UsernameNotFoundException("User not found with id: " + id);
        }
        return appUserMapper.toAppUserResponseDTO(userOptional.get());
    }

    @Transactional
    public void login(UserLoginRequestDTO entity) {
        Optional<AppUser> userOptional = appUserRepo.findByEmail(entity.email());
        if (userOptional.isEmpty()) {
            throw new UsernameNotFoundException("User not found with email: " + entity.email());
        }
        AppUser user = userOptional.get();
        if (!user.getPassword().equals(entity.password())) {
            throw new UsernameNotFoundException("Invalid password for email: " + entity.email());
        }
    }

    @Transactional
    public UsersResponseDTO saveUser(UsersRequestDTO entity) {
        appUserRepo.findByEmail(entity.email()).ifPresent(u -> {
            throw new UsernameNotFoundException("User already exists with email: " + entity.email());
        });

        appUserRepo.findByUserName(entity.userName()).ifPresent(u -> {
            throw new UsernameNotFoundException("User already exists with username: " + entity.userName());
        });
        AppUser user = new AppUser();
        user.setEmail(entity.email());
        user.setUserName(entity.userName());
        user.setPassword(entity.password());
        user.setRole(entity.role());
        user.setActive(true);
        user.setEnabled(true);
        user.setEmailVerified(true);
        AppUser savedUser = appUserRepo.save(user);
        return appUserMapper.toAppUserResponseDTO(savedUser);
    }

}

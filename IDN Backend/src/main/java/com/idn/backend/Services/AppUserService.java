package com.idn.backend.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.idn.backend.dto.response.UsersResponseDTO;
import com.idn.backend.entity.AppUser;
import com.idn.backend.exception.UsernameNotFoundException;
import com.idn.backend.mapper.AppUserMapper;
import com.idn.backend.repo.AppUserRepo;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AppUserService {

    private final AppUserRepo appUserRepo;
    private final AppUserMapper appUserMapper;

    public List<UsersResponseDTO> findAllUsers() {
        List<AppUser> users = appUserRepo.findAll();
        List<UsersResponseDTO> userDTOs = appUserMapper.toAppUserResponseDTOList(users);
        return userDTOs;
    }

    public UsersResponseDTO findUserById(Long id) {
        Optional<AppUser> userOptional = appUserRepo.findById(id);
        if (userOptional.isEmpty()) {
            throw new UsernameNotFoundException("User not found with id: " + id);
        }
        return appUserMapper.toAppUserResponseDTO(userOptional.get());
    }

}

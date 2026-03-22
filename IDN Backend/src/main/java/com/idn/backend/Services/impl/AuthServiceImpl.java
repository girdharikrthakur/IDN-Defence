package com.idn.backend.services.impl;

import java.util.Optional;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.idn.backend.dto.request.RegistrationDTO;
import com.idn.backend.entity.AppUser;
import com.idn.backend.entity.Role;
import com.idn.backend.exception.UserAlreadyExistsException;
import com.idn.backend.repo.AppUserRepo;
import com.idn.backend.services.AuthService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final AppUserRepo appUserRepo;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void userSignUp(RegistrationDTO reg) {

        Optional<AppUser> fetchedUserByEmail = appUserRepo.findByEmail(reg.email());
        Optional<AppUser> fetchedUserByUsername = appUserRepo.findByUserName(reg.userName());

        if (fetchedUserByEmail.isPresent()) {

            throw new UserAlreadyExistsException(
                    "User already Exist with email " + reg.email() + " Try Signing in with different email");
        }

        if (fetchedUserByUsername.isPresent()) {

            throw new UserAlreadyExistsException(
                    "User already Exist with username " + reg.userName() + " Try Signing in with different username");
        }
        AppUser newUser = new AppUser();
        newUser.setUserName(reg.userName());
        newUser.setEmail(reg.email());
        newUser.setPassword(passwordEncoder.encode(reg.password()));
        newUser.setRole(Role.ROLE_USER);
        appUserRepo.save(newUser);

    }

}

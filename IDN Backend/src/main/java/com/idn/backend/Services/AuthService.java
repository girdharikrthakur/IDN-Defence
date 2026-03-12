package com.idn.backend.Services;

import java.io.IOException;
import java.util.Optional;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.idn.backend.DTO.RequestDTO.RegistrationDTO;
import com.idn.backend.ExceptionHandler.UserAlreadyExistsException;
import com.idn.backend.Model.Role;
import com.idn.backend.Model.AppUser;
import com.idn.backend.Repo.AppUserRepo;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AppUserRepo appUserRepo;
    private final PasswordEncoder passwordEncoder;

    public void userSignUp(RegistrationDTO reg) throws IOException {

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

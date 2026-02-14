package com.idn.backend.Services;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.idn.backend.DTO.RegisterRequest;
import com.idn.backend.ExceptionHandler.UserAlreadyExistsException;
import com.idn.backend.Model.Role;
import com.idn.backend.Model.AppUser;
import com.idn.backend.Repo.AppUserRepo;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AppUserRepo appUserRepo;
    private final EmailService emailService;
    private final PasswordEncoder passwordEncoder;

    public void userSignUp(RegisterRequest request) throws IOException {

        Optional<AppUser> fetchedUserByEmail = appUserRepo.findByEmail(request.getEmail());
        Optional<AppUser> fetchedUserByUsername = appUserRepo.findByUserName(request.getUsername());

        if (fetchedUserByEmail.isPresent() | fetchedUserByUsername.isPresent()) {

            throw new UserAlreadyExistsException(
                    request.getUsername() + "User already Exist , Try Signing in with " + request.getEmail() + "or "
                            + request.getUsername());
        }

        String token = UUID.randomUUID().toString();

        AppUser user = new AppUser();
        user.setUserName(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setEmail(request.getEmail());

        user.setRole(Role.ROLE_USER);
        user.setEmailVerified(false);
        user.setVerificationToken(token);
        user.setTokenExpiry(LocalDateTime.now().plusHours(24));

        appUserRepo.save(user);

        emailService.sendVerificationEmail(user.getEmail(), token);

    }

}

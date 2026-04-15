package com.idn.backend.service.impl;

import java.util.Optional;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.idn.backend.Utils.JwtUtil;
import com.idn.backend.dto.request.RegistrationDTO;
import com.idn.backend.dto.response.TokenResponse;
import com.idn.backend.entity.AppUser;
import com.idn.backend.entity.Role;
import com.idn.backend.exception.UserAlreadyExistsException;
import com.idn.backend.repo.AppUserRepo;
import com.idn.backend.service.AuthService;

import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final AppUserRepo appUserRepo;
    private final SessionService sessionService;
    private final JwtUtil tokenService;
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
        sessionService.createSession(newUser);
    }

    public TokenResponse login(String email,
            String password) {
        AppUser user = appUserRepo.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new RuntimeException("Invalid credentials");
        }

        return sessionService.createSession(user);
    }

    public TokenResponse completeOAuth(String token, String username, String password) {

        if (token == null || token.isBlank()) {
            throw new RuntimeException("Token is missing");
        }

        Claims claims = tokenService.validateToken(token);

        if (!"OAUTH_TEMP".equals(claims.get("type"))) {
            throw new RuntimeException("Invalid Token");
        }

        String email = claims.getSubject();

        AppUser newUser = new AppUser();

        newUser.setEmail(email);
        newUser.setUserName(username);
        newUser.setPassword(passwordEncoder.encode(password));
        newUser.setRole(Role.ROLE_USER);
        newUser.setEmailVerified(true);
        newUser.setEnabled(true);
        newUser.setActive(true);

        appUserRepo.save(newUser);

        return sessionService.createSession(newUser);
    }

}
package com.idn.backend.Controller;

import java.time.LocalDateTime;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.idn.backend.DTO.RegisterRequest;
import com.idn.backend.Model.Users;
import com.idn.backend.Repo.UsersRepo;
import com.idn.backend.Services.AuthService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/public/api/v1/signup")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:5173")
public class AuthController {

    private final AuthService authService;
    private final UsersRepo userRepository;

    @PostMapping("/register")
    public String register(@RequestBody RegisterRequest request) throws Exception {
        authService.userSignUp(request);
        return "Registration successful. Please verify your email.";
    }

    @GetMapping("/verify")
    public String verifyEmail(@RequestParam String token) {

        Users user = userRepository.findByVerificationToken(token)
                .orElseThrow(() -> new RuntimeException("Invalid token"));

        if (user.getTokenExpiry().isBefore(LocalDateTime.now())) {
            return "Verification link expired";
        }

        user.setEmailVerified(true);
        user.setVerificationToken(null);
        user.setTokenExpiry(null);

        userRepository.save(user);

        return "Email verified successfully";
    }
}
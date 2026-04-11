package com.idn.backend.controller;

import java.io.IOException;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.idn.backend.dto.request.RegistrationDTO;
import com.idn.backend.dto.response.TokenResponse;
import com.idn.backend.services.impl.AuthServiceImpl;
import com.idn.backend.services.impl.SessionService;
import com.idn.backend.dto.request.LoginRequestDTO;
import com.idn.backend.dto.request.OAuthCompleteRequest;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
@CrossOrigin(origins = "http://localhost:5173")
public class AuthController {

    private final AuthServiceImpl authService;
    private final SessionService sessionService;

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody RegistrationDTO reg) throws IOException {

        authService.userSignUp(reg);
        return ResponseEntity.ok("Registration successful");

    }

    @PostMapping("/login")
    public TokenResponse login(@RequestBody LoginRequestDTO req) {
        return authService.login(req.email(), req.password());
    }

    @PostMapping("/refresh")
    public TokenResponse refresh(@RequestBody Map<String, String> req) {
        return sessionService.refresh(req.get("refreshToken"));

    }

    @PostMapping("/complete")
    public TokenResponse complete(@RequestBody OAuthCompleteRequest req) {
        return authService.completeOAuth(req.token(), req.username(), req.password());
    }

}

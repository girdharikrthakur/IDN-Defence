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
import com.idn.backend.service.impl.AuthServiceImpl;
import com.idn.backend.service.impl.SessionService;

import jakarta.servlet.http.HttpServletRequest;

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
    public ResponseEntity<String> register(@RequestBody RegistrationDTO reg, HttpServletRequest request)
            throws IOException {

        authService.userSignUp(reg, request);
        return ResponseEntity.ok("Registration successful");

    }

    @PostMapping("/login")
    public TokenResponse login(@RequestBody LoginRequestDTO req, HttpServletRequest request) {
        return authService.login(req.email(), req.password(), request);
    }

    @PostMapping("/refresh")
    public TokenResponse refresh(@RequestBody Map<String, String> req, HttpServletRequest request) {
        return sessionService.refresh(req.get("refreshToken"), request);
    }

    @PostMapping("/complete")
    public TokenResponse complete(@RequestBody OAuthCompleteRequest req, HttpServletRequest request) {
        return authService.completeOAuth(req.token(), req.username(), req.password(), request);
    }

}

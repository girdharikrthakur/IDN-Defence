package com.idn.backend.controller;

import java.io.IOException;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.idn.backend.dto.request.RegistrationDTO;
import com.idn.backend.dto.request.UserLoginRequestDTO;
import com.idn.backend.entity.ApplicationConstants;
import com.idn.backend.services.impl.AuthServiceImpl;
import com.idn.backend.services.impl.UserServiceImpl;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
@CrossOrigin(origins = "http://localhost:5173")
public class AuthController {

    private final AuthServiceImpl authService;
    private final UserServiceImpl userService;

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody RegistrationDTO reg) throws IOException {

        authService.userSignUp(reg);
        return ResponseEntity.ok("Registration successful");

    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UserLoginRequestDTO request) {
        String jwt = userService.login(request);

        return ResponseEntity.ok()
                .header(ApplicationConstants.JWT_HEADER, jwt)
                .body("Login successful");
    }

}

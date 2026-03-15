package com.idn.backend.Controller;

import java.io.IOException;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.idn.backend.DTO.RequestDTO.RegistrationDTO;
import com.idn.backend.Services.AuthService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:5173")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody RegistrationDTO reg) throws IOException {

        authService.userSignUp(reg);
        return ResponseEntity.ok("Registration successful");

    }
}

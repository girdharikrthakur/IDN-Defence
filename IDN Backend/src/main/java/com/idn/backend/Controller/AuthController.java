package com.idn.backend.Controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.idn.backend.DTO.AuthResposneDTO;
import com.idn.backend.Services.AuthService;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/public/api/v1/signup")
@AllArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping
    public ResponseEntity<AuthResposneDTO> userSignUp(
            @RequestParam String username,
            @RequestParam String email,
            @RequestParam String password) {

        AuthResposneDTO saveduser = authService.userSignUp(username, email, password);

        return ResponseEntity.ok(saveduser);

    }
}

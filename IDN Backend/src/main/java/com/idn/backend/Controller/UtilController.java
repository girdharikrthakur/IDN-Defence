package com.idn.backend.Controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.idn.backend.DTO.RequestDTO.LoginRequestDTO;

import jakarta.validation.Valid;

@Controller
public class UtilController {

    @GetMapping("/home")
    public String getMethodName() {
        return "index.html";
    }

    @GetMapping("/error/denied")
    public String accessDenied() {
        return "denied.html";
    }

    @GetMapping("/error/unauthorized")
    public String unauthorized() {
        return "unauthorized.html";
    }

    @GetMapping("/login")
    public String login() {
        return "login.html";
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequestDTO request) {

        return ResponseEntity.ok("Login success");
    }

    @GetMapping("/signup")
    public String signup() {
        return "signup.html";
    }

    @GetMapping("/api/me")
    public ResponseEntity<?> currentUser(Authentication auth) {

        if (auth == null || !auth.isAuthenticated()) {
            return ResponseEntity.status(401).build();
        }

        return ResponseEntity.ok(auth.getName());
    }

}

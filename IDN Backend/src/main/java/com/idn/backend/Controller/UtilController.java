package com.idn.backend.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UtilController {
    @GetMapping("/api/me")
    public ResponseEntity<?> currentUser(Authentication auth) {

        if (auth == null || auth instanceof AnonymousAuthenticationToken) {
            return ResponseEntity.status(401).build();
        }

        return ResponseEntity.ok(auth.getName());
    }
}

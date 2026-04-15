package com.idn.backend.controller;

import java.util.List;
import java.util.Map;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class UtilController {

    @GetMapping("/me")
    public Map<String, Object> getCurrentUser(Authentication auth) {

        // ✅ Check authentication properly
        if (auth == null || !auth.isAuthenticated()
                || auth.getPrincipal().equals("anonymousUser")) {

            return Map.of(
                    "loggedIn", false,
                    "username", null,
                    "roles", List.of(),
                    "isAdmin", false);
        }

        // ✅ Extract roles safely
        List<String> roles = auth.getAuthorities() == null
                ? List.of()
                : auth.getAuthorities()
                        .stream()
                        .map(GrantedAuthority::getAuthority)
                        .toList();

        // ✅ Check admin role
        boolean isAdmin = roles.contains("ROLE_ADMIN");

        return Map.of(
                "loggedIn", true,
                "username", auth.getName(),
                "roles", roles,
                "isAdmin", isAdmin);
    }
}
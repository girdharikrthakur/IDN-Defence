package com.idn.backend.controller;

import java.util.List;
import java.util.Map;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
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

        if (auth == null || !auth.isAuthenticated()
                || auth instanceof AnonymousAuthenticationToken) {
            return Map.of("loggedIn", false);
        }

        List<String> roles = auth.getAuthorities() == null
                ? List.of()
                : auth.getAuthorities()
                        .stream()
                        .map(GrantedAuthority::getAuthority)
                        .toList();

        boolean isAdmin = roles.contains("ROLE_ADMIN");

        return Map.of(
                "loggedIn", true,
                "username", auth.getName(),
                "roles", roles,
                "isAdmin", isAdmin);
    }
}
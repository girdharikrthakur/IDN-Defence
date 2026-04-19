package com.idn.backend.controller;

import java.util.List;
import java.util.Map;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.idn.backend.entity.AppUser;
import com.idn.backend.exception.UserNotFoundException;
import com.idn.backend.repo.AppUserRepo;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class UtilController {

        private final AppUserRepo appUserRepo;

        @GetMapping("/me")
        public Map<String, Object> getCurrentUser(Authentication auth) {

                String defautDpUrl = "https://media.istockphoto.com/id/1495088043/vector/user-profile-icon-avatar-or-person-icon-profile-picture-portrait-symbol-default-portrait.jpg?s=612x612&w=0&k=20&c=dhV2p1JwmloBTOaGAtaA3AW1KSnjsdMt7-U_3EZElZ0=";

                if (auth == null || !auth.isAuthenticated()
                                || auth.getPrincipal().equals("anonymousUser")) {

                        return Map.of(
                                        "loggedIn", false,
                                        "username", null,
                                        "roles", List.of(),
                                        "isAdmin", false,
                                        "dpUrl", defautDpUrl);
                }

                String email = auth.getName();

                AppUser user = appUserRepo.findByEmail(email)
                                .orElseThrow(() -> new UserNotFoundException("User not found with email: " + email));
                String dpUrl = user.getDpUrl() != null ? user.getDpUrl() : defautDpUrl;
                List<String> roles = auth.getAuthorities() == null
                                ? List.of()
                                : auth.getAuthorities()
                                                .stream()
                                                .map(GrantedAuthority::getAuthority)
                                                .toList();

                boolean isAdmin = roles.contains("ROLE_ADMIN");

                String username = user.getUserName();

                return Map.of(
                                "loggedIn", true,
                                "username", username,
                                "roles", roles,
                                "isAdmin", isAdmin,
                                "dpUrl", dpUrl);
        }
}
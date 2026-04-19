package com.idn.backend.service.impl;

import java.io.IOException;
import java.util.Optional;

import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.idn.backend.Utils.JwtUtil;
import com.idn.backend.entity.AppUser;
import com.idn.backend.repo.AppUserRepo;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class OAuth2SuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final AppUserRepo userRepo;
    private final SessionService sessionService;
    private final JwtUtil tokenService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
            HttpServletResponse response,
            Authentication authentication) throws IOException {

        OAuth2User oAuthUser = (OAuth2User) authentication.getPrincipal();

        String name = oAuthUser.getAttribute("name");
        String email = oAuthUser.getAttribute("email");
        String login = oAuthUser.getAttribute("login"); // GitHub username
        String avatar = oAuthUser.getAttribute("avatar_url");

        String provider = ((OAuth2AuthenticationToken) authentication)
                .getAuthorizedClientRegistrationId();

        String providerId = oAuthUser.getAttribute("id").toString();

        // fallback for GitHub email
        if (email == null) {
            email = provider + "_" + providerId + "@oauth.com";
        }

        Optional<AppUser> optionalUser = userRepo.findByEmail(email);

        if (optionalUser.isPresent()) {
            AppUser user = optionalUser.get();

            sessionService.createSession(user, request);

            String jwt = tokenService.generateAccessToken(user);

            String redirectUrl = "http://localhost:5173/oauth-success?token=" + jwt;

            getRedirectStrategy().sendRedirect(request, response, redirectUrl);

        } else {

            String tempToken = tokenService.generateTempToken(
                    email,
                    name,
                    provider,
                    providerId,
                    login,
                    avatar);

            String redirectUrl = "http://localhost:5173/complete-registration?token=" + tempToken;

            getRedirectStrategy().sendRedirect(request, response, redirectUrl);
        }
    }
}

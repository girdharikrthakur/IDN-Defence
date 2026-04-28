package com.idn.backend.service.impl;

import java.io.IOException;
import java.util.Optional;

import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
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

        Object principal = authentication.getPrincipal();

        String provider = ((OAuth2AuthenticationToken) authentication)
                .getAuthorizedClientRegistrationId();

        String name = null;
        String email = null;
        String login = null;
        String avatar = null;
        String picture = null;
        String providerId = null;

        // GOOGLE (OIDC)
        if ("google".equals(provider) && principal instanceof OidcUser oidcUser) {

            name = String.valueOf(oidcUser.getClaims().get("name"));
            email = String.valueOf(oidcUser.getClaims().get("email"));
            picture = String.valueOf(oidcUser.getClaims().get("picture"));

            providerId = String.valueOf(oidcUser.getClaims().get("sub"));
        } else if ("github".equals(provider) && principal instanceof OAuth2User oAuthUser) {

            name = oAuthUser.getAttribute("name");
            email = oAuthUser.getAttribute("email");
            login = oAuthUser.getAttribute("login");
            avatar = oAuthUser.getAttribute("avatar_url");
            providerId = oAuthUser.getAttribute("id").toString();
        } else if ("facebook".equals(provider) && principal instanceof OAuth2User oAuthUser) {

            name = oAuthUser.getAttribute("name");
            email = oAuthUser.getAttribute("email");
            login = oAuthUser.getAttribute("login");
            providerId = oAuthUser.getAttribute("id").toString();
            avatar = oAuthUser.getAttribute("pcture");
        }

        // fallback for GitHub email
        if (email == null) {
            email = provider + "_" + providerId.toString() + "@oauth.com";
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
                    avatar,
                    picture);

            String redirectUrl = "http://localhost:5173/complete-registration?token=" + tempToken;

            getRedirectStrategy().sendRedirect(request, response, redirectUrl);
        }
    }
}

package com.idn.backend.service.impl;

import java.util.Map;

import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Service;

@Service
public class OAuthService extends DefaultOAuth2UserService {

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {

        OAuth2User oAuth2User = super.loadUser(userRequest);

        System.out.println("PROVIDER: " +
                userRequest.getClientRegistration().getRegistrationId());

        System.out.println("OAuth Attributes START");

        // 🔥 Handle Google (OIDC)
        if (oAuth2User instanceof OidcUser oidcUser) {

            Map<String, Object> claims = oidcUser.getClaims();

            claims.forEach((key, value) -> {
                System.out.println(key + " : " + value);
            });

        } else {
            // 🔥 Handle GitHub, etc.
            Map<String, Object> attributes = oAuth2User.getAttributes();

            attributes.forEach((key, value) -> {
                System.out.println(key + " : " + value);
            });
        }

        System.out.println("OAuth Attributes END");

        return oAuth2User;
    }
}
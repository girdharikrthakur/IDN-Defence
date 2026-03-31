package com.idn.backend.services.impl;

import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.stream.Collectors;

import javax.crypto.SecretKey;

import org.springframework.core.env.Environment;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import com.idn.backend.dto.request.UserLoginRequestDTO;
import com.idn.backend.entity.ApplicationConstants;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserServiceImpl {

        private final AuthenticationManager authenticationManager;
        private final Environment env;

        public String login(UserLoginRequestDTO entity) {

                // ✅ Step 1: Authenticate user
                Authentication authentication = authenticationManager.authenticate(
                                new UsernamePasswordAuthenticationToken(
                                                entity.email(),
                                                entity.password()));

                // ✅ Step 2: Check authentication
                if (authentication != null && authentication.isAuthenticated()) {

                        // ✅ Step 3: Get secret key
                        String secret = env.getProperty(
                                        ApplicationConstants.JWT_SECRET_KEY,
                                        ApplicationConstants.JWT_SECRET_DEFAULT_VALUE);

                        SecretKey secretKey = Keys.hmacShaKeyFor(
                                        secret.getBytes(StandardCharsets.UTF_8));

                        // ✅ Step 4: Generate JWT
                        String jwt = Jwts.builder()
                                        .issuer("IDN MEDIA")
                                        .subject("JWT TOKEN")
                                        .claim("username", authentication.getName())
                                        .claim("authorities",
                                                        authentication.getAuthorities().stream()
                                                                        .map(GrantedAuthority::getAuthority)
                                                                        .collect(Collectors.joining(",")))
                                        .issuedAt(new Date())
                                        .expiration(new Date(System.currentTimeMillis() + 30000000))
                                        .signWith(secretKey)
                                        .compact();

                        // ✅ Step 5: Return token
                        return jwt;
                }

                throw new RuntimeException("Invalid credentials");
        }
}
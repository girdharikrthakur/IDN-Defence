package com.idn.backend.service.impl;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.idn.backend.Utils.JwtUtil;
import com.idn.backend.dto.response.TokenResponse;
import com.idn.backend.entity.AppUser;
import com.idn.backend.entity.UserSession;
import com.idn.backend.repo.UserSessionRepo;

import lombok.RequiredArgsConstructor;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class SessionService {

    private final UserSessionRepo repo;
    private final JwtUtil tokenService;

    private final long REFRESH_DAYS = 1000 * 60 * 7;

    @Transactional
    public TokenResponse createSession(AppUser user) {

        String accessToken = tokenService.generateAccessToken(user);

        String refreshToken = UUID.randomUUID().toString();
        UserSession session = new UserSession();
        session.setUser(user);
        session.setRefreshToken(refreshToken);
        session.setRevoked(false);
        session.setCreatedAt(LocalDateTime.now());
        session.setExpiresAt(Instant.now().plus(REFRESH_DAYS, ChronoUnit.DAYS));
        repo.save(session);
        return new TokenResponse(accessToken, refreshToken);

    }

    @Transactional
    public TokenResponse refresh(String token) {

        UserSession session = repo.findByRefreshToken(token)
                .orElseThrow(() -> new RuntimeException("Session not found"));

        if (session.isRevoked()) {
            revokeAll(session.getUser());
            throw new RuntimeException("TOKEN REUSE DETECTED");
        }
        session.setRevoked(true);
        repo.save(session);
        return createSession(session.getUser());

    }

    @Transactional
    private void revokeAll(AppUser user) {
        List<UserSession> sessionList = repo.findByUser(user);
        sessionList.forEach(s -> s.setRevoked(true));
        repo.saveAll(sessionList);
    }

}
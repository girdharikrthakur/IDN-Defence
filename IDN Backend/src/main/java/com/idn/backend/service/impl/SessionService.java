package com.idn.backend.service.impl;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.idn.backend.Utils.JwtUtil;
import com.idn.backend.dto.response.TokenResponse;
import com.idn.backend.entity.AppUser;
import com.idn.backend.entity.UserSession;
import com.idn.backend.repo.UserSessionRepo;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SessionService {

    private final UserSessionRepo repo;
    private final JwtUtil tokenService;

    private final long REFRESH_DAYS = 1000 * 60 * 7;

    public TokenResponse createSession(AppUser user, HttpServletRequest request) {
        String accessToken = tokenService.generateAccessToken(user);

        // IP Address
        String ip = request.getHeader("X-Forwarded-For");
        if (ip == null || ip.isEmpty()) {
            ip = request.getRemoteAddr();
        }

        // Device Name
        String device = request.getHeader("User-Agent");

        // Device ID (from frontend)
        String deviceId = request.getHeader("X-Device-Id");

        String refreshToken = UUID.randomUUID().toString();
        UserSession userSession = new UserSession();
        userSession.setUser(user);
        userSession.setRefreshToken(refreshToken);
        userSession.setDeviceId(deviceId);
        userSession.setDeviceName(device);
        userSession.setIpAddress(ip);
        userSession.setLastUsedAt(LocalDateTime.now());
        userSession.setRevoked(false);
        userSession.setExpiresAt(Instant.now().plus(REFRESH_DAYS, ChronoUnit.DAYS));
        repo.save(userSession);
        return new TokenResponse(accessToken, refreshToken);

    }

    public TokenResponse refresh(String token, HttpServletRequest request) {

        UserSession userSession = repo.findByRefreshToken(token)
                .orElseThrow(() -> new RuntimeException("Session not found"));

        if (userSession.isRevoked()) {
            revokeAll(userSession.getUser());
            throw new RuntimeException("TOKEN REUSE DETECTED");
        }
        userSession.setRevoked(true);
        repo.save(userSession);
        return createSession(userSession.getUser(), request);

    }

    private void revokeAll(AppUser user) {
        List<UserSession> sessionList = repo.findByUser(user);
        sessionList.forEach(s -> s.setRevoked(true));
        repo.saveAll(sessionList);
    }

}
package com.idn.backend.Utils;

import java.nio.charset.StandardCharsets;
import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.stereotype.Component;

import com.idn.backend.entity.AppUser;
import com.idn.backend.entity.ApplicationConstants;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class JwtUtil {

    private final String SECRET = ApplicationConstants.JWT_SECRET_DEFAULT_VALUE;

    private final long ACCESS_EXP = 1000 * 60 * 15;
    private final long TEMP_EXP = 1000 * 60 * 10;

    private SecretKey getKey() {
        return Keys.hmacShaKeyFor(SECRET.getBytes(StandardCharsets.UTF_8));
    }

    // Access Token with user details and role, expires in 15 mins

    public String generateAccessToken(AppUser user) {
        return Jwts.builder()
                .subject(user.getEmail())
                .claim("userId", user.getId())
                .claim("role", user.getRole().name())
                .claim("type", "ACCESS")
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + ACCESS_EXP))
                .signWith(getKey())
                .compact();
    }

    // Temporary Token for OAuth users with email, name and provider details,
    // expires in 10 mins

    public String generateTempToken(String email, String name, String provider, String providerId, String login,
            String avatar) {

        return Jwts.builder()
                .subject(email)
                .claim("name", name)
                .claim("provider", provider)
                .claim("providerId", providerId)
                .claim("login", login)
                .claim("avatar", avatar)
                .claim("type", "OAUTH_TEMP")
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + TEMP_EXP))
                .signWith(getKey())
                .compact();
    }

    // Token validation for both Access and Temp tokens, returns claims if valid
    public Claims validateToken(String token) {
        return Jwts.parser()
                .verifyWith(getKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    // Extract all claims without validating expiration (used for extracting details
    // from temp token during OAuth completion)

    private Claims extractAllClaims(String token) {
        return Jwts.parser()
                .verifyWith(getKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    // Helper methods to extract specific claims

    public String extractEmail(String token) {
        return extractAllClaims(token).getSubject();
    }

    public String extractRole(String token) {
        return extractAllClaims(token).get("role", String.class);
    }

    public String extractProvider(String token) {
        return extractAllClaims(token).get("provider", String.class);
    }

    public String extractType(String token) {
        return extractAllClaims(token).get("type", String.class);
    }

    // Validate if token is valid and belongs to the given email (used for securing
    // temp token endpoints)

    public boolean isTokenValid(String token, String email) {
        return extractEmail(token).equals(email) && !isTokenExpired(token);
    }

    public boolean isAccessToken(String token) {
        return "ACCESS".equals(extractType(token));
    }

    public boolean isTempToken(String token) {
        return "OAUTH_TEMP".equals(extractType(token));
    }

    private boolean isTokenExpired(String token) {
        return extractAllClaims(token).getExpiration().before(new Date());
    }

    public String extractDpUrl(String token) {
        return extractAllClaims(token).get("avatar", String.class);
    }
}
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

    // ACCESS TOKEN (for API calls)

    public String generateAccessToken(AppUser user) {
        return Jwts.builder()
                .subject(user.getEmail())
                .claim("userId", user.getId())
                .claim("role", user.getRole())
                .claim("type", "ACCESS")
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + ACCESS_EXP))
                .signWith(getKey())
                .compact();
    }

    // TEMP TOKEN (Used To register OAuth user)

    public String generateTempToken(String email, String name) {
        return Jwts.builder()
                .subject(email)
                .claim("name", name)
                .claim("type", "OAUTH_TEMP")
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + TEMP_EXP))
                .signWith(getKey())
                .compact();
    }

    // Token Validation
    public Claims validateToken(String token) {
        return Jwts.parser()
                .verifyWith(getKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    // Extract Username
    public String extractUsername(String token) {
        return extractAllClaims(token).get("username", String.class);
    }

    // Extract Authorities
    public String extractAuthorities(String token) {
        return extractAllClaims(token).get("authorities", String.class);
    }

    // Validate Token
    public boolean validateToken(String token, String username) {
        final String extractedUsername = extractUsername(token);
        return (extractedUsername.equals(username) && !isTokenExpired(token));
    }

    // Check Expiration
    private boolean isTokenExpired(String token) {
        return extractAllClaims(token).getExpiration().before(new Date());
    }

    // Extract Claims
    private Claims extractAllClaims(String token) {
        return Jwts.parser()
                .verifyWith(getKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }
}
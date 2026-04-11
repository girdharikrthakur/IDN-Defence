package com.idn.backend.repo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.idn.backend.entity.AppUser;
import com.idn.backend.entity.UserSession;

public interface UserSessionRepo extends JpaRepository<UserSession, Long> {

    // 🔍 Find session using refresh token
    Optional<UserSession> findByRefreshToken(String token);

    // 🔍 Get all sessions of a user (for logout all / reuse detection)
    List<UserSession> findByUser(AppUser user);

}

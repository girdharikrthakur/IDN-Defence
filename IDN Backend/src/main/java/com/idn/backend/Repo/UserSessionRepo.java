package com.idn.backend.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.idn.backend.entity.UserSession;

public interface UserSessionRepo extends JpaRepository<UserSession, Long> {

}

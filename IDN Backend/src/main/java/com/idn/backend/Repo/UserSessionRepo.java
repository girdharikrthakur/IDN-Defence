package com.idn.backend.Repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.idn.backend.Model.UserSession;

public interface UserSessionRepo extends JpaRepository<UserSession, Long> {

}

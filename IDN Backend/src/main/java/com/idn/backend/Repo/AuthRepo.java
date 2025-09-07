package com.idn.backend.Repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.idn.backend.Model.UserAuth;

public interface AuthRepo extends JpaRepository<UserAuth, Long> {

    Optional<UserAuth> findByEmail();

    Optional<UserAuth> findByUsername();

}

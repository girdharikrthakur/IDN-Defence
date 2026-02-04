package com.idn.backend.Repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.idn.backend.Model.Users;

public interface UsersRepo extends JpaRepository<Users, Long> {

    Optional<Users> findByEmail(String email);

    Optional<Users> findByUserName(String userName);

}

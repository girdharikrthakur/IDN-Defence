package com.idn.backend.Repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.idn.backend.Model.User;

public interface UserRepo extends JpaRepository<User, Long> {

}
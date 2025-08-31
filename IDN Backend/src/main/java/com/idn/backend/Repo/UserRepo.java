package com.idn.backend.Repo;

<<<<<<< HEAD
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.idn.backend.Model.UserAuth;

public interface UserRepo extends JpaRepository<UserAuth, Long> {

    Optional<UserAuth> findByEmail(String username);
=======
import org.springframework.data.jpa.repository.JpaRepository;

import com.idn.backend.Model.User;

public interface UserRepo extends JpaRepository<User, Long> {
>>>>>>> 4b67cafe63b444f2d2e9ac432da94992e59320f0

}
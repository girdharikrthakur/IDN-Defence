package com.idn.backend.repo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.idn.backend.dto.UserDTO;
import com.idn.backend.entity.AppUser;

public interface AppUserRepo extends JpaRepository<AppUser, Long> {

    Optional<AppUser> findByEmail(String email);

    Optional<AppUser> findByUserName(String userName);

    @Query("SELECT COUNT(u) FROM AppUser u")
    long countUsers();

    @Query("""
                SELECT new com.idn.backend.dto.UserDTO(
                    u.id,
                    u.userName,
                    u.email,
                    u.role,
                    COUNT(p)
                )
                FROM AppUser u
                LEFT JOIN u.posts p
                GROUP BY u.id, u.userName, u.email, u.role
            """)
    List<UserDTO> getAllUsers();

}

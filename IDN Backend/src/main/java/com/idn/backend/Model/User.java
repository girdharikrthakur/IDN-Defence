package com.idn.backend.Model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "USERS")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "USER_ID")
    private Long id;

    @Column(name = "NAME")
    private String name;

    @Column(name = "USER_NAME")
    private String username;

    @Column(name = "Email")
    private String email;

    @Column(name = "PHONE_NO")
    private Long phoneno;

    @Column(name = "USER_ROLE")
    private Role role;

    @Column(name = "IS_ACTIVE")
    private boolean isActive;
}

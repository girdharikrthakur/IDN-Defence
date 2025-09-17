package com.idn.backend.Model;

import java.util.List;

import org.springframework.stereotype.Component;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Component
@Table(name = "USERS")
public class UserAccount {

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

    @OneToMany(mappedBy = "user")
    private List<Comment> comments;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Draft> darfts;

    @Enumerated
    @Column(name = "USER_ROLE")
    private Role role;

    @Column(name = "IS_ACTIVE")
    private boolean isActive;

    public enum Role {
        ADMIN,
        AUTHOR,
        READER
    }

}

package com.idn.backend.entity;

import java.time.Instant;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(indexes = {
        @Index(name = "idx_refresh_token", columnList = "refreshToken"),
        @Index(name = "idx_user_id", columnList = "user_id")
}, name = "user_sessions")
public class UserSession extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String refreshToken;

    private Instant expiresAt;

    private boolean revoked;

    private String deviceId;

    private String deviceName;

    private String ipAddress;

    private LocalDateTime lastUsedAt;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private AppUser user;
}

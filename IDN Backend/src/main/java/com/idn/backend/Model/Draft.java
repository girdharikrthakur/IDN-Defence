package com.idn.backend.Model;

import java.time.LocalDateTime;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "drafts")
public class Draft extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    @Column(columnDefinition = "TEXT")
    private String content;

    private String imageUrl;

    @JoinColumn(name = "user_id")
    @ManyToOne
    private AppUser user;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private boolean isPublished;

}

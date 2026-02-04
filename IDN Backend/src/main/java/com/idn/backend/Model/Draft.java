package com.idn.backend.Model;

import java.time.LocalDateTime;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "DRAFTS")
public class Draft {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "DRAFT_ID")
    private Long id;

    @Column(name = "TITLE")
    private String title;

    @Column(name = "CONTENT", columnDefinition = "TEXT")
    private String content;

    @Column(name = "IMG_URL")
    private String imageUrl;

    @JoinColumn(name = "user_id")
    @ManyToOne
    private Users user;

    @Column(name = "CREATED_DATE")
    private LocalDateTime createdAt;

    @Column(name = "UPDATED_DATE")
    private LocalDateTime updatedAt;

    @Column(name = "IS_PUBLISHED")
    private boolean isPublished;

}

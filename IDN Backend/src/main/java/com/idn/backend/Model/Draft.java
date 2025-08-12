package com.idn.backend.Model;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "POSTS")
public class Draft {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "POST_ID")
    private Long id;

    @Column(name = "TITLE")
    private String title;

    @Column(name = "CONTENT", columnDefinition = "TEXT")
    private String content;

    @Column(name = "IMG_URL")
    private String imageUrl;

    @Column(name = "CREATED_DATE")
    private LocalDateTime createdAt;

    @Column(name = "UPDATED_DATE")
    private LocalDateTime updatedAt;

    @Column(name = "IS_PUBLISHED")
    private boolean isPublished;

}
package com.idn.backend.DTO;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.idn.backend.Model.Author;
import com.idn.backend.Model.Category;
import com.idn.backend.Model.Comment;

import lombok.Data;

@Data
public class PostRequestDTO {
    private String title;

    private String content;

    private String imgUrl;

    private Category category;

    private Author author;

    private List<Comment> comments = new ArrayList<>();

    private int views;

    private LocalDateTime publishedAt;

    private LocalDateTime updatedAt;

    private boolean isPublished;
}

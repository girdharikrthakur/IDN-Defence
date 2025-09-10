package com.idn.backend.Controller;

import java.io.IOException;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.idn.backend.DTO.PostResponseDTO;
import com.idn.backend.Model.Category;
import com.idn.backend.Model.Post;

import com.idn.backend.Services.PostService;

import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping("public/api/v1/posts")
@CrossOrigin(origins = "http://localhost:5173")
public class PostController {

    private final PostService postService;

    @PostMapping
    public ResponseEntity<Post> createPost(
            @RequestParam() String title,
            @RequestParam() String content,
            @RequestParam Category category,
            @RequestParam() MultipartFile file) throws IOException {

        Post savedPost = postService.savePost(title, content, category, file);

        return ResponseEntity.ok(savedPost);
    }

    @GetMapping
    public ResponseEntity<Page<PostResponseDTO>> searchPosts(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        Page<PostResponseDTO> posts = postService.getAllPost(page, size);

        return ResponseEntity.ok(posts);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Post> getPostById(Long id) {

        Post post = postService.findAllPostsById(id);
        return ResponseEntity.ok(post);
    }

}

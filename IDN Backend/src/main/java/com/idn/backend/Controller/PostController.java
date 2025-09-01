package com.idn.backend.Controller;

import java.io.IOException;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
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

    @GetMapping()
    public ResponseEntity<List<PostResponseDTO>> getAllPosts() {

        List<PostResponseDTO> posts = postService.findAllPosts();
        return ResponseEntity.ok(posts);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Post> getPostById(Long id) {

        Post post = postService.findAllPostsById(id);
        return ResponseEntity.ok(post);
    }

}

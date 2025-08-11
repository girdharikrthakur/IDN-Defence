package com.idn.backend.Controller;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.idn.backend.Model.Post;
import com.idn.backend.Repo.PostRepo;
import com.idn.backend.Services.GCSService;

@RestController
@RequestMapping("/posts")
public class PostController {

    @Autowired
    private PostRepo postRepo;

    @Autowired
    private GCSService gcsService;

    @PostMapping
    public ResponseEntity<Post> createPost(
            @RequestParam() String title,
            @RequestParam() String content,

            @RequestParam() MultipartFile file) {

        try {
            // Upload image to GCP
            String imageUrl = gcsService.uploadFile(file);

            // Create post entity
            Post post = new Post();
            post.setTitle(title);
            post.setContent(content);
            post.setImageUrl(imageUrl);
            post.setCreatedAt(LocalDateTime.now());
            post.setUpdatedAt(LocalDateTime.now());

            // Save to DB
            Post savedPost = postRepo.save(post);

            return ResponseEntity.ok(savedPost);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
}

package com.idn.backend.Controller;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.idn.backend.Model.Post;
import com.idn.backend.Repo.PostRepo;
import com.idn.backend.Services.GCSService;

@RestController
@RequestMapping("/api/v1/posts")
@CrossOrigin(origins = "http://localhost:5173")
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
            String imageUrl = gcsService.uploadFile(file);

            Post post = new Post();
            post.setTitle(title);
            post.setContent(content);
            post.setImgUrl(imageUrl);
            post.setPublishedAt(LocalDateTime.now());
            post.setUpdatedAt(LocalDateTime.now());

            // Save to DB
            Post savedPost = postRepo.save(post);

            return ResponseEntity.ok(savedPost);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @GetMapping()
    public ResponseEntity<List<Post>> getAllPosts() {

        List<Post> posts = postRepo.findAll();
        return ResponseEntity.ok(posts);
    }

}

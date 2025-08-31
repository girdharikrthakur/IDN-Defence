package com.idn.backend.Controller;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.idn.backend.Model.Post;
import com.idn.backend.Repo.PostRepo;
import com.idn.backend.Services.GCSService;

@RestController
@RequestMapping("private/api/v1/posts")
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

            Post savedPost = postRepo.save(post);

            return ResponseEntity.ok(savedPost);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @GetMapping()
    public ResponseEntity<List<Post>> getAllPosts() {

        List<Post> posts = postRepo.findAll();
        return ResponseEntity.ok(posts);
    }

}

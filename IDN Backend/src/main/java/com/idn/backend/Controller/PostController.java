package com.idn.backend.Controller;

<<<<<<< HEAD
import java.io.IOException;
=======
>>>>>>> 4b67cafe63b444f2d2e9ac432da94992e59320f0
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
<<<<<<< HEAD
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
=======
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.idn.backend.Model.Draft;
import com.idn.backend.Model.Post;
import com.idn.backend.Repo.PostRepo;
import com.idn.backend.Services.DraftService;
import com.idn.backend.Services.GCSService;
import com.idn.backend.Services.PostService;

@RestController
@RequestMapping("/posts")
>>>>>>> 4b67cafe63b444f2d2e9ac432da94992e59320f0
public class PostController {

    @Autowired
    private PostRepo postRepo;

    @Autowired
<<<<<<< HEAD
    private GCSService gcsService;

    @PostMapping
    public ResponseEntity<Post> createPost(
            @RequestParam() String title,
            @RequestParam() String content,
            @RequestParam() MultipartFile file) {
=======
    private DraftService draftService;

    @Autowired
    private PostService postService;

    @Autowired
    private GCSService gcsService;

    @GetMapping()
    public ResponseEntity<List<Post>> getAllPost() {
        List<Post> posts = postService.getAllPost();

        return ResponseEntity.ok(posts);
    }

    @GetMapping("/{id}")
    public ResponseEntity<List<Post>> getPostById(@RequestParam int id) {
        List<Post> posts = postService.getPost(id);
        if (posts.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } else {
            return ResponseEntity.ok(posts);
        }
    }

    @PostMapping
    public ResponseEntity<?> createPost(
            @RequestParam String title,
            @RequestParam String content,
            @RequestParam MultipartFile file,
            @RequestParam boolean isPublished) {
>>>>>>> 4b67cafe63b444f2d2e9ac432da94992e59320f0

        try {
            String imageUrl = gcsService.uploadFile(file);

<<<<<<< HEAD
            Post post = new Post();
            post.setTitle(title);
            post.setContent(content);
            post.setImgUrl(imageUrl);
            post.setPublishedAt(LocalDateTime.now());
            post.setUpdatedAt(LocalDateTime.now());

            Post savedPost = postRepo.save(post);

            return ResponseEntity.ok(savedPost);
        } catch (IOException e) {
=======
            if (isPublished) {
                // Save to Post entity
                Post post = new Post();
                post.setTitle(title);
                post.setContent(content);
                post.setImageUrl(imageUrl);
                post.setCreatedAt(LocalDateTime.now());
                post.setUpdatedAt(LocalDateTime.now());

                Post savedPost = postService.savePost(post);
                return ResponseEntity.ok(savedPost);
            } else {
                // Save to Draft entity
                Draft draft = new Draft();
                draft.setTitle(title);
                draft.setContent(content);
                draft.setImageUrl(imageUrl);
                draft.setCreatedAt(LocalDateTime.now());
                draft.setUpdatedAt(LocalDateTime.now());

                Draft savedDraft = draftService.savePost(draft);
                return ResponseEntity.ok(savedDraft);
            }
        } catch (Exception e) {
>>>>>>> 4b67cafe63b444f2d2e9ac432da94992e59320f0
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

<<<<<<< HEAD
    @GetMapping()
    public ResponseEntity<List<Post>> getAllPosts() {

        List<Post> posts = postRepo.findAll();
        return ResponseEntity.ok(posts);
    }

=======
>>>>>>> 4b67cafe63b444f2d2e9ac432da94992e59320f0
}

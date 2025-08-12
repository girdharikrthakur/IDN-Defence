package com.idn.backend.Controller;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
public class PostController {

    @Autowired
    private PostRepo postRepo;

    @Autowired
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

        try {
            String imageUrl = gcsService.uploadFile(file);

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
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

}

package com.idn.backend.Controller;

import com.idn.backend.DTO.RequestDTO.PostRequestDTO;
import com.idn.backend.DTO.ResponseDTO.PostResponseDTO;
import com.idn.backend.Services.PostService;

import java.io.IOException;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.PutMapping;

@RestController
@RequestMapping("/private/post")
public class PostController {

    private final PostService postService;

    PostController(PostService postService) {
        this.postService = postService;
    }

    @PostMapping(consumes = "multipart/form-data")
    public ResponseEntity<String> postMethodName(
            @RequestPart("dto") PostRequestDTO dto,
            @RequestPart(value = "file", required = false) List<MultipartFile> file) throws IOException {

        PostResponseDTO savedPost = postService.savePost(dto, file);
        return ResponseEntity.ok("Post Created with id: " + savedPost.getId());
    }

    @GetMapping
    public ResponseEntity<List<PostResponseDTO>> getPosts(
            @RequestParam(required = false) Long cursor,
            @RequestParam(defaultValue = "10") int limit) {

        return ResponseEntity.ok(postService.getPosts(cursor, limit));
    }

    @GetMapping("/{id}")
    public ResponseEntity<PostResponseDTO> getPostById(@PathVariable Long id) {

        PostResponseDTO post = postService.getPostById(id);
        return ResponseEntity.ok().body(post);

    }

    @GetMapping("/search")
    public ResponseEntity<List<PostResponseDTO>> searchPosts(@RequestParam String query) {

        List<PostResponseDTO> posts = postService.searchPosts(query);
        return ResponseEntity.ok(posts);
    }

    @PutMapping(value = "/{id}", consumes = "multipart/form-data")
    public ResponseEntity<String> updatePost(
            @PathVariable Long id,
            @RequestPart("dto") PostRequestDTO dto,
            @RequestPart(value = "file", required = false) List<MultipartFile> file) throws IOException {

        PostResponseDTO savedPost = postService.updatePost(id, dto, file);
        return ResponseEntity.ok("Post Updated with id: " + savedPost.getId());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePost(@PathVariable Long id) {

        postService.deletePost(id);
        return ResponseEntity.ok("Post deleted successfully");
    }

}

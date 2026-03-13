package com.idn.backend.Controller;

import com.idn.backend.DTO.RequestDTO.PostRequestDTO;
import com.idn.backend.DTO.ResponseDTO.PostResponseDTO;
import com.idn.backend.Services.PostService;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@RequestMapping("/private/post")
public class PostController {

    private final PostService postService;

    PostController(PostService postService) {
        this.postService = postService;
    }

    @PostMapping()
    public ResponseEntity<PostResponseDTO> postMethodName(@RequestBody PostRequestDTO dto) {

        PostResponseDTO savedPost = postService.savePost(dto);
        return ResponseEntity.ok().body(savedPost);
    }

    @GetMapping()
    public ResponseEntity<List<PostResponseDTO>> getPosts() {
        List<PostResponseDTO> postList = postService.getPosts();
        return ResponseEntity.ok().body(postList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PostResponseDTO> getPostById(@PathVariable Long id) {

        PostResponseDTO post = postService.getPostById(id);
        return ResponseEntity.ok().body(post);

    }

    @GetMapping("/search")
    public ResponseEntity<List<PostResponseDTO>> searchPosts(@RequestParam String query) {

        throw new UnsupportedOperationException("Unimplemented method 'searchPosts'");
    }

}

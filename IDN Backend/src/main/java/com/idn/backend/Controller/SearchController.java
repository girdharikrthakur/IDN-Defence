package com.idn.backend.Controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.idn.backend.DTO.PostResponseDTO;
import com.idn.backend.Services.PostService;

import lombok.RequiredArgsConstructor;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/posts")
public class SearchController {

    private final PostService postService;

    @GetMapping("/search")
    public ResponseEntity<List<PostResponseDTO>> search(@RequestParam String keyword) {
        List<PostResponseDTO> posts = postService.search(keyword);
        return ResponseEntity.ok(posts);
    }
}

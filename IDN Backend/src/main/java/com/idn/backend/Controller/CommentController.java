package com.idn.backend.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.idn.backend.dto.request.CommentRequest;
import com.idn.backend.dto.response.ApiResponse;
import com.idn.backend.dto.response.CommentDTO;
import com.idn.backend.service.impl.CommentsServiceImpl;

import jakarta.websocket.server.PathParam;
import lombok.RequiredArgsConstructor;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@RequiredArgsConstructor
@RequestMapping("/comment")
public class CommentController {

    private final CommentsServiceImpl commentsService;

    @PreAuthorize("hasAnyRole('USER','AUTHOR','ADMIN')")
    @PostMapping()
    public ResponseEntity<ApiResponse<CommentDTO>> saveComment(
            Authentication authentication,
            @RequestBody CommentRequest dto) {

        String email = authentication.getName();
        CommentDTO comment = commentsService.createComment(dto, email);
        ApiResponse<CommentDTO> response = new ApiResponse<>("Comment Saved", comment);

        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/{postId}")
    public ResponseEntity<ApiResponse<List<CommentDTO>>> getCommentByPostId(@PathVariable Long postId) {
        List<CommentDTO> response = commentsService.getCommentByPostId(postId);
        ApiResponse<List<CommentDTO>> apiResponse = new ApiResponse<>("Comments Found", response);
        return ResponseEntity.ok().body(apiResponse);
    }

    @GetMapping()
    public ResponseEntity<ApiResponse<List<CommentDTO>>> getCommentByPostId() {
        List<CommentDTO> response = commentsService.findAllParentComment();
        ApiResponse<List<CommentDTO>> apiResponse = new ApiResponse<>("Comments Found", response);
        return ResponseEntity.ok().body(apiResponse);
    }

}

package com.idn.backend.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.idn.backend.dto.request.CommentRequestDTO;
import com.idn.backend.dto.response.ApiResponse;
import com.idn.backend.dto.response.CommentResponseDTO;
import com.idn.backend.service.impl.CommentsServiceImpl;

import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/comment")
public class CommentController {

    private final CommentsServiceImpl commentsService;

    @PreAuthorize("hasAnyRole('USER','AUTHOR','ADMIN')")
    @GetMapping()
    public ResponseEntity<ApiResponse<CommentResponseDTO>> saveComment(
            Authentication authentication,
            @RequestBody CommentRequestDTO dto) {

        String email = authentication.getName();
        CommentResponseDTO comment = commentsService.saveComment(dto, email);
        ApiResponse<CommentResponseDTO> response = new ApiResponse<>("Comment Saved", comment);

        return ResponseEntity.ok().body(response);
    }

}

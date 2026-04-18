package com.idn.backend.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.idn.backend.dto.request.CommentRequestDTO;
import com.idn.backend.dto.response.CommentResponseDTO;
import com.idn.backend.entity.AppUser;
import com.idn.backend.entity.Comment;
import com.idn.backend.entity.Post;
import com.idn.backend.exception.PostNotFoundException;
import com.idn.backend.mapper.CommentMapper;
import com.idn.backend.repo.AppUserRepo;
import com.idn.backend.repo.CommentRepo;
import com.idn.backend.repo.PostRepo;

import lombok.RequiredArgsConstructor;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CommentsServiceImpl {

    private final CommentRepo commentRepo;
    private final CommentMapper commentMapper;
    private final PostRepo postRepo;
    private final AppUserRepo appUserRepo;

    @Transactional
    public CommentResponseDTO saveComment(CommentRequestDTO req, String email) {

        AppUser user = appUserRepo.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        String username = user.getUserName();

        Post post;
        Comment parent = null;

        if (req.getParentId() != null) {
            parent = commentRepo.findById(req.getParentId())
                    .orElseThrow(() -> new RuntimeException("Parent not found"));

            post = parent.getPost();
        } else {
            post = postRepo.findById(req.getPostId())
                    .orElseThrow(() -> new PostNotFoundException("Post not found"));
        }

        Comment comment = new Comment();
        comment.setContent(req.getContent());
        comment.setPost(post);
        comment.setUser(user);
        comment.setParent(parent);
        comment.setCreatedBy(username);
        Comment savedComment = commentRepo.save(comment);

        return commentMapper.toResponse(savedComment);
    }

}

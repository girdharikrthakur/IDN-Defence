package com.idn.backend.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.idn.backend.dto.request.CommentRequest;
import com.idn.backend.dto.response.CommentDTO;
import com.idn.backend.entity.Comment;
import com.idn.backend.entity.Post;
import com.idn.backend.exception.PostNotFoundException;
import com.idn.backend.mapper.CommentMapper;
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

    public CommentDTO createComment(CommentRequest request, String email) {

        Post post = postRepo.findById(request.postId())
                .orElseThrow(() -> new RuntimeException("Post not found"));

        Comment comment = commentMapper.toEntity(request);

        comment.setPost(post);

        if (request.parentId() != null) {

            Comment parent = commentRepo.findById(request.parentId())
                    .orElseThrow(() -> new RuntimeException("Parent comment not found"));

            comment.setParent(parent);
        }

        Comment saved = commentRepo.save(comment);

        return commentMapper.toResponse(saved);
    }

    public List<CommentDTO> getCommentByPostId(Long postId) {

        Post post = postRepo.findById(postId)
                .orElseThrow(() -> new PostNotFoundException("Post not found"));

        List<Comment> comments = commentRepo.findByPostIdAndParentIsNull(postId);

        if (comments.isEmpty()) {
            throw new RuntimeException("No comments found");
        }

        return comments.stream()
                .map(commentMapper::toResponse)
                .toList();
    }

    public List<CommentDTO> findAllParentComment() {
        List<Comment> comments = commentRepo.findByParentNull();
        if (comments.isEmpty()) {
            throw new RuntimeException("No comment found");
        }
        return comments.stream()
                .map(commentMapper::toResponse)
                .toList();

    }

}

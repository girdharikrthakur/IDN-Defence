package com.idn.backend.Services;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.idn.backend.DTO.CommentRequestDTO;
import com.idn.backend.DTO.CommentResponseDTO;
import com.idn.backend.Mapper.CommentMapper;
import com.idn.backend.Model.Comment;
import com.idn.backend.Repo.CommentRepo;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CommentsService {

    private final CommentRepo commentRepo;
    private final CommentMapper commentMapper;

    public CommentResponseDTO saveComment(CommentRequestDTO comment) {

        Comment savedComment = commentMapper.toEntity(comment);
        Comment response = commentRepo.save(savedComment);
        return commentMapper.toResponseDto(response);

    }

    public List<CommentResponseDTO> getCommnets() {

        List<Comment> comments = commentRepo.findAll();
        return commentMapper.toResponseDtoList(comments);
    }

    public CommentResponseDTO getCommentsById(Long id) {
        Comment comment = commentRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Comment not found with id " + id));

        return commentMapper.toResponseDto(comment);
    }

    public CommentResponseDTO deleteComment(Long id) {

        Comment comment = commentRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Comment not found with id " + id));
        commentRepo.deleteById(id);
        return commentMapper.toResponseDto(comment);
    }

    public CommentResponseDTO editComment(Long id, CommentRequestDTO dto, Principal principal) {
        Comment comment = commentRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Comment not found by id" + id));

        if (!comment.getUser().getUsername().equals(principal.getName())) {
            throw new RuntimeException("You are not allowed to edit this comment!");

        }

        comment.setContent(dto.getContent());
        comment.setUpdatedAt(LocalDateTime.now());

        Comment savedComment = commentRepo.save(comment);

        return commentMapper.toResponseDto(savedComment);

    }

    public List<CommentResponseDTO> getReplies(Long parentId) {

        Comment parent = commentRepo.findById(parentId)
                .orElseThrow(() -> new RuntimeException("reply not found with parentId: " + parentId));

        return parent.getReplies()
                .stream()
                .map(commentMapper::toResponseDto)
                .toList();

    }

}

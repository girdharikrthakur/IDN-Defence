package com.idn.backend.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.idn.backend.entity.Comment;

public interface CommentRepo extends JpaRepository<Comment, Long> {
    List<Comment> findByPostIdAndParentIsNull(Long postId);

    List<Comment> findByParentNull();

}

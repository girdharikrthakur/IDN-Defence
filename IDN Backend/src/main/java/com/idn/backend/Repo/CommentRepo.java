package com.idn.backend.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.idn.backend.entity.Comment;

public interface CommentRepo extends JpaRepository<Comment, Long> {

}

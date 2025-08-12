package com.idn.backend.Repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.idn.backend.Model.Comments;

public interface CommentRepo extends JpaRepository<Comments, Long> {

}

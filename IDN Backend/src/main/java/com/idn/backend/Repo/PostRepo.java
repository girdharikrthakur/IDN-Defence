package com.idn.backend.Repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.idn.backend.Model.Post;

public interface PostRepo extends JpaRepository<Post, Long> {

}

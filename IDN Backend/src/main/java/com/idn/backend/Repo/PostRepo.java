package com.idn.backend.Repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.idn.backend.Model.Post;

public interface PostRepo extends JpaRepository<Post, Long> {

    List<Post> findByTitleContainingIgnoreCaseOrContentContainingIgnoreCase(String title, String content);

}

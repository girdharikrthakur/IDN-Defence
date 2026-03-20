package com.idn.backend.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.idn.backend.entity.PostView;

public interface PostViewRepo extends JpaRepository<PostView, Long> {

}

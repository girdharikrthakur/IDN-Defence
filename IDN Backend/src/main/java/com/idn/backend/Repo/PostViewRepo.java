package com.idn.backend.Repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.idn.backend.Model.PostView;

public interface PostViewRepo extends JpaRepository<PostView, Long> {

}

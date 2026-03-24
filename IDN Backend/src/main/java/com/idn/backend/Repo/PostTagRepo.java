package com.idn.backend.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.idn.backend.entity.Post;
import com.idn.backend.entity.PostTag;

public interface PostTagRepo extends JpaRepository<PostTag, Long> {

    void deleteByPost(Post post);

}

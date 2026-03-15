package com.idn.backend.Repo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.idn.backend.Model.Post;

public interface PostRepo extends JpaRepository<Post, Long> {

    List<Post> findByTitleContainingIgnoreCaseOrContentContainingIgnoreCase(String title, String content);

    Optional<Post> findByIdAndDeletedFalse(Long id);

    List<Post> findByDeletedFalseOrderByIdDesc(Pageable pageable);

    List<Post> findByDeletedFalseAndIdLessThanOrderByIdDesc(Long cursor, Pageable pageable);

}

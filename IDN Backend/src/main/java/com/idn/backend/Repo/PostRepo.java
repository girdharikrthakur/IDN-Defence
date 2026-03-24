package com.idn.backend.repo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.idn.backend.dto.CategoryStatsDTO;
import com.idn.backend.dto.PostDTO;
import com.idn.backend.entity.Post;

public interface PostRepo extends JpaRepository<Post, Long> {

    List<Post> findByTitleContainingIgnoreCaseOrContentContainingIgnoreCase(String title, String content);

    Optional<Post> findByIdAndDeletedFalse(Long id);

    List<Post> findByDeletedFalseOrderByIdDesc(Pageable pageable);

    List<Post> findByDeletedFalseAndIdLessThanOrderByIdDesc(Long cursor, Pageable pageable);

    // Admin Dashboard APIs

    @Query("SELECT COUNT(p) FROM Post p WHERE p.deleted = false")
    long countPosts();

    @Query("SELECT COUNT(v) FROM PostView v")
    long sumViews();

    @Query("""
                SELECT new com.idn.backend.dto.CategoryStatsDTO(
                    c.name,
                    COUNT(p),
                    COUNT(v)
                )
                FROM Post p
                JOIN p.categories c
                LEFT JOIN PostView v ON v.post = p
                WHERE p.deleted = false
                GROUP BY c.name
            """)
    List<CategoryStatsDTO> getCategoryStats();

    @Query("""
                SELECT new com.idn.backend.dto.PostDTO(
                    p.id,
                    p.title,
                    p.author.email,
                    COUNT(v)
                )
                FROM Post p
                LEFT JOIN PostView v ON v.post = p
                WHERE p.deleted = false
                GROUP BY p.id, p.title, p.author.email
            """)
    List<PostDTO> getAllPosts();

}

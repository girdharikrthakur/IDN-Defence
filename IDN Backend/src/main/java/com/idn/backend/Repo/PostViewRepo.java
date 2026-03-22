package com.idn.backend.repo;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.idn.backend.entity.PostView;

public interface PostViewRepo extends JpaRepository<PostView, Long> {

    // Unique per day per user/session
    @Query("""
            SELECT COUNT(DISTINCT
                CASE
                    WHEN pv.username IS NOT NULL THEN pv.username
                    ELSE pv.sessionId
                END)
            FROM PostView pv
            WHERE pv.post.id = :postId
            AND pv.viewDate = :date
            """)
    Long countUniqueViewsPerUserPerSession(Long postId, LocalDate date);

    // Total views per post
    @Query("""
            SELECT COUNT(DISTINCT
                CASE
                    WHEN pv.username IS NOT NULL THEN pv.username
                    ELSE pv.sessionId
                END)
            FROM PostView pv
            WHERE pv.post.id = :postId
            AND pv.viewDate = :date
            """)
    Long countUniqueViews(Long postId, LocalDate date);

    // Views today
    @Query("SELECT COUNT(pv) FROM PostView pv WHERE pv.post.id = :postId AND pv.viewDate = :date")
    Long countViewsToday(Long postId, LocalDate date);

    // Unique views
    @Query("SELECT COUNT(DISTINCT pv.ipAddress) FROM PostView pv WHERE pv.post.id = :postId")
    Long countUniqueViews(Long postId);

    // Most Viewed Post
    @Query("""
            SELECT pv.post.id, COUNT(pv) as viewCount
            FROM PostView pv
            GROUP BY pv.post.id
            ORDER BY viewCount DESC
            """)
    List<Object[]> findMostViewedPosts(Pageable pageable);

    // Trending Post

    @Query("""
            SELECT pv.post.id, COUNT(pv) as viewCount
            FROM PostView pv
            WHERE pv.createdAt >= :since
            GROUP BY pv.post.id
            ORDER BY viewCount DESC
            """)
    List<Object[]> findTrendingPosts(LocalDateTime since, Pageable pageable);

}

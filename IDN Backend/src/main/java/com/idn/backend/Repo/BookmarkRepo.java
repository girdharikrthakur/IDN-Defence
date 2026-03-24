package com.idn.backend.repo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.idn.backend.entity.AppUser;
import com.idn.backend.entity.Bookmark;

public interface BookmarkRepo extends JpaRepository<Bookmark, Long> {

    List<Bookmark> findByUser(AppUser user);

    Page<Bookmark> findByUser(AppUser user, Pageable pageable);

    Optional<Bookmark> findByIdAndUser(Long id, AppUser user);

}

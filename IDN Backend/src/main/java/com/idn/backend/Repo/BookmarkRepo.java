package com.idn.backend.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.idn.backend.entity.Bookmark;

public interface BookmarkRepo extends JpaRepository<Bookmark, Long> {

}

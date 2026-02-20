package com.idn.backend.Repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.idn.backend.Model.Bookmark;

public interface BookmarkRepo extends JpaRepository<Bookmark, Long> {

}

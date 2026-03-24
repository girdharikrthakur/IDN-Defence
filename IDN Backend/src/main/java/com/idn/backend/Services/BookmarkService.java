package com.idn.backend.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.idn.backend.dto.BookmarkDTO;

public interface BookmarkService {

    public BookmarkDTO saveBookmark(BookmarkDTO dto);

    public BookmarkDTO deleteBookmark(Long id);

    public Page<BookmarkDTO> getUserBookmarks(Pageable pageable);

}

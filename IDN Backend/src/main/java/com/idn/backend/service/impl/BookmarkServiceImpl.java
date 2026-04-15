package com.idn.backend.service.impl;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import com.idn.backend.dto.BookmarkDTO;
import com.idn.backend.entity.AppUser;
import com.idn.backend.entity.Bookmark;
import com.idn.backend.mapper.BookmarkMapper;
import com.idn.backend.repo.BookmarkRepo;
import com.idn.backend.service.BookmarkService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BookmarkServiceImpl implements BookmarkService {

    private final BookmarkMapper bookmarkMapper;
    private final BookmarkRepo bookmarkRepo;
    private final HelperServiceImpl helperServices;

    @PreAuthorize("hasAnyRole('AUTHOR','ADMIN','USER')")
    @Override
    public BookmarkDTO saveBookmark(BookmarkDTO dto) {

        Bookmark bookmark = bookmarkMapper.toEntity(dto);
        Bookmark savedBookmark = bookmarkRepo.save(bookmark);
        return bookmarkMapper.toDTO(savedBookmark);

    }

    @PreAuthorize("hasAnyRole('AUTHOR','ADMIN','USER')")
    public Page<BookmarkDTO> getUserBookmarks(Pageable pageable) {

        AppUser user = helperServices.getCurrentUser();

        Page<Bookmark> bookmarks = bookmarkRepo.findByUser(user, pageable);

        return bookmarks.map(bookmarkMapper::toDTO);
    }

    @PreAuthorize("hasAnyRole('AUTHOR','ADMIN','USER')")
    @Override
    public BookmarkDTO deleteBookmark(Long id) {

        AppUser user = helperServices.getCurrentUser();

        Bookmark bookmark = bookmarkRepo.findByIdAndUser(id, user)
                .orElseThrow(() -> new RuntimeException("Bookmark not found or not authorized"));

        bookmarkRepo.delete(bookmark);

        return bookmarkMapper.toDTO(bookmark);
    }

}

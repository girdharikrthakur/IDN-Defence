package com.idn.backend.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.idn.backend.dto.BookmarkDTO;
import com.idn.backend.dto.response.ApiResponse;
import com.idn.backend.services.impl.BookmarkServiceImpl;

import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/bookmark")
@RequiredArgsConstructor
public class BookmarkControler {

    private final BookmarkServiceImpl bookmarkService;

    @PostMapping()
    public ResponseEntity<ApiResponse<BookmarkDTO>> saveBookmark(@RequestBody BookmarkDTO dto) {

        BookmarkDTO savedBookmark = bookmarkService.saveBookmark(dto);
        ApiResponse<BookmarkDTO> response = new ApiResponse<>("Bookamark Saved", savedBookmark);

        return ResponseEntity.ok().body(response);

    }

    @GetMapping()
    public ResponseEntity<ApiResponse<Page<BookmarkDTO>>> getBookmarks(Pageable pageable) {

        Page<BookmarkDTO> fetchedBookmarks = bookmarkService.getUserBookmarks(pageable);
        ApiResponse<Page<BookmarkDTO>> response = new ApiResponse<>("Fetched bookmarks", fetchedBookmarks);
        return ResponseEntity.ok().body(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<BookmarkDTO>> deleteBookmark(Long id) {
        BookmarkDTO deletedBookmark = bookmarkService.deleteBookmark(id);
        ApiResponse<BookmarkDTO> response = new ApiResponse<>("Bookmark Deleted", deletedBookmark);

        return ResponseEntity.ok().body(response);
    }

}

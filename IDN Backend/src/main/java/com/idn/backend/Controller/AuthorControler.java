package com.idn.backend.Controller;

import com.idn.backend.DTO.AuthorDTO;
import com.idn.backend.Services.AuthorService;

import lombok.RequiredArgsConstructor;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/private/api/v1/author")
public class AuthorControler {

    private final AuthorService authorService;

    @PostMapping()
    public ResponseEntity<AuthorDTO> saveAuthor(@RequestBody AuthorDTO authorDTO) {

        AuthorDTO savedAuthor = authorService.saveAuthor(authorDTO);

        return ResponseEntity.ok(savedAuthor);

    }

    @GetMapping()
    public ResponseEntity<List<AuthorDTO>> getAuthor() {

        List<AuthorDTO> savedAuthors = authorService.getAuthor();
        return ResponseEntity.ok(savedAuthors);
    }

    @GetMapping("{id}")
    public ResponseEntity<AuthorDTO> getAuthorById(@PathVariable Long id) {

        AuthorDTO author = authorService.getAuthorById(id);
        return ResponseEntity.ok(author);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<AuthorDTO> deleteAuthorById(@PathVariable Long id) {

        AuthorDTO author = authorService.deleteAuthorById(id);
        return ResponseEntity.ok(author);
    }

    @PutMapping("{id}")
    public ResponseEntity<AuthorDTO> editAuthor(@PathVariable Long id, @RequestBody AuthorDTO dto) {

        AuthorDTO authorToEdit = authorService.editAuthor(id, dto);

        return ResponseEntity.ok(authorToEdit);
    }

}

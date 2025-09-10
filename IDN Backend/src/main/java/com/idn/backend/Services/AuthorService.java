package com.idn.backend.Services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.idn.backend.DTO.AuthorDTO;
import com.idn.backend.Mapper.AuthorMapper;
import com.idn.backend.Model.Author;
import com.idn.backend.Repo.AuthorRepo;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class AuthorService {

    private final AuthorRepo authorRepo;
    private final AuthorMapper authorMapper;

    public AuthorDTO saveAuthor(AuthorDTO authorDTO) {

        Author author = authorMapper.toEntity(authorDTO);
        Author savedAuthor = authorRepo.save(author);
        return authorMapper.toReponDTO(savedAuthor);
    }

    public List<AuthorDTO> getAuthor() {
        List<Author> authors = authorRepo.findAll();
        return authorMapper.toListAuthorDTO(authors);
    }

    public AuthorDTO getAuthorById(Long id) {

        Author author = authorRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Author Dosen't exists with id: " + id));
        return authorMapper.toReponDTO(author);
    }

    public AuthorDTO deleteAuthorById(Long id) {
        Author author = authorRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Author dossen't exist by id: " + id));

        authorRepo.deleteById(id);
        return authorMapper.toReponDTO(author);
    }

    public AuthorDTO editAuthor(Long id, AuthorDTO dto) {

        Author author = authorRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Author dosen't exists with id: " + id));

        authorMapper.updateAuthorFromDto(dto, author);

        Author updatedAuthor = authorRepo.save(author);

        return authorMapper.toReponDTO(updatedAuthor);

    }

}

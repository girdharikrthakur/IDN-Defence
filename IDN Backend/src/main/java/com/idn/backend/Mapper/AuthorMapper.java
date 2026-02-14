package com.idn.backend.Mapper;

import java.util.List;

import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import com.idn.backend.DTO.AuthorDTO;
import com.idn.backend.Model.Author;

public interface AuthorMapper extends BaseMapper {

    @Mapping(target = "id", ignore = true)
    Author toEntity(AuthorDTO dto);

    AuthorDTO toReponDTO(Author author);

    List<AuthorDTO> toListAuthorDTO(List<Author> author);

    @Mapping(target = "id", ignore = true)
    void updateAuthorFromDto(AuthorDTO dto, @MappingTarget Author author);
}

package com.idn.backend.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.idn.backend.dto.BookmarkDTO;
import com.idn.backend.entity.Bookmark;

@Mapper(componentModel = "spring")
public interface BookmarkMapper {

    @Mapping(target = "postid", source = "post.id")
    BookmarkDTO toDTO(Bookmark bookmark);

    List<BookmarkDTO> toDTOList(List<Bookmark> bookmark);

    @Mapping(target = "post.id", source = "postid")
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "updatedBy", ignore = true)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "user", ignore = true)
    Bookmark toEntity(BookmarkDTO dto);

}

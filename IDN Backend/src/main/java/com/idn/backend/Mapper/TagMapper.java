package com.idn.backend.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.idn.backend.dto.TagDTO;
import com.idn.backend.entity.Tag;

@Mapper(componentModel = "spring")
public interface TagMapper {

    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "updatedBy", ignore = true)
    @Mapping(target = "postTags", ignore = true)
    Tag toEntity(TagDTO dto);

    TagDTO toDTO(Tag tag);

    List<TagDTO> toDTOList(List<Tag> tagList);

}

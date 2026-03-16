package com.idn.backend.Mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.idn.backend.DTO.RequestDTO.PostRequestDTO;
import com.idn.backend.DTO.ResponseDTO.PostResponseDTO;
import com.idn.backend.Model.Post;

@Mapper(componentModel = "spring")
public interface PostMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "author", ignore = true)
    @Mapping(target = "categories", ignore = true)
    @Mapping(target = "mediaList", ignore = true)
    @Mapping(target = "comments", ignore = true)
    @Mapping(target = "views", ignore = true)
    @Mapping(target = "postTags", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "updatedBy", ignore = true)
    @Mapping(target = "deleted", ignore = true)
    Post toPost(PostRequestDTO dto);

    @Mapping(target = "authorName", ignore = true)
    @Mapping(target = "categories", ignore = true)
    PostResponseDTO toPostResponseDTO(Post post);

    List<PostResponseDTO> toPostResponseDTOList(List<Post> posts);

}

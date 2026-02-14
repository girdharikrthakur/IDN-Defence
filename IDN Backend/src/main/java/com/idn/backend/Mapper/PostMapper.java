package com.idn.backend.Mapper;

import java.util.List;
import java.util.Optional;

import org.mapstruct.Mapping;

import com.idn.backend.DTO.PostRequestDTO;
import com.idn.backend.DTO.PostResponseDTO;
import com.idn.backend.Model.Post;

public interface PostMapper extends BaseMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "updatedAt", expression = "java(map(dto.getUpdatedAt()))")
    @Mapping(target = "views", expression = "java(map(dto.getViews()))")
    @Mapping(target = "category", expression = "java(map(dto.getCategory()))")
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "mediaList", ignore = true)
    @Mapping(target = "postTags", ignore = true)
    Post toEntity(PostRequestDTO dto);

    PostResponseDTO toResponseDTO(Post post);

    List<PostResponseDTO> toResponseDTOs(List<Post> qureyPosts);

    @Mapping(target = "id", ignore = true)
    Post updatePostFromDto(Post post, Optional<Post> fetchedPost);

}

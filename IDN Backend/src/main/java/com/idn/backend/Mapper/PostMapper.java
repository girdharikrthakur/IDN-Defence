package com.idn.backend.Mapper;

import java.util.List;
import java.util.Optional;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.idn.backend.DTO.PostRequestDTO;
import com.idn.backend.DTO.PostResponseDTO;
import com.idn.backend.Model.Post;

@Mapper(componentModel = "spring")
public interface PostMapper {

    @Mapping(target = "id", ignore = true)
    Post toEntity(PostRequestDTO dto);

    @Mapping(source = "category.name", target = "categoryName")
    @Mapping(source = "author.name", target = "authorName")
    PostResponseDTO toResponseDTO(Post post);

    List<PostResponseDTO> toResponseDTOs(List<Post> qureyPosts);

    @Mapping(target = "id", ignore = true)
    Post updatePostFromDto(Post post, Optional<Post> fetchedPost);
}

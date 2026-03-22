package com.idn.backend.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.idn.backend.dto.response.CommentResponseDTO;
import com.idn.backend.entity.Comment;

@Mapper(componentModel = "spring")
public interface CommentMapper {

    @Mapping(source = "post.id", target = "postId")
    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "parent.id", target = "parentId")
    @Mapping(target = "replies", ignore = true)
    CommentResponseDTO toResponse(Comment comment);

}
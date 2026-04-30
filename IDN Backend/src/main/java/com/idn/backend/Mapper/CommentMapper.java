package com.idn.backend.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.idn.backend.dto.request.CommentRequest;
import com.idn.backend.dto.response.CommentDTO;
import com.idn.backend.entity.Comment;

@Mapper(componentModel = "spring")
public interface CommentMapper {

    @Mapping(target = "parent", ignore = true)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "replies", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "updatedBy", ignore = true)
    @Mapping(target = "post", ignore = true)
    @Mapping(target = "user", ignore = true)
    Comment toEntity(CommentRequest dto);

    @Mapping(target = "replies", expression = "java(mapReplies(comment.getReplies()))")
    CommentDTO toResponse(Comment comment);

    default List<CommentDTO> mapReplies(List<Comment> replies) {
        if (replies == null)
            return List.of();

        return replies.stream()
                .map(this::toResponse)
                .toList();
    }
}
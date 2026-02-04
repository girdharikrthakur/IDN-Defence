package com.idn.backend.Mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.idn.backend.DTO.CommentRequestDTO;
import com.idn.backend.DTO.CommentResponseDTO;
import com.idn.backend.Model.Comment;

@Mapper(componentModel = "spring")
public interface CommentMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "user", ignore = true)
    @Mapping(target = "post", ignore = true)
    @Mapping(target = "parentComment", ignore = true)
    @Mapping(target = "replies", ignore = true)
    Comment toEntity(CommentRequestDTO dto);

    @Mapping(source = "user.userName", target = "username")
    @Mapping(source = "parentComment.id", target = "parentCommentId")
    CommentResponseDTO toResponseDto(Comment comment);

    List<CommentResponseDTO> toResponseDtoList(List<Comment> comments);

}

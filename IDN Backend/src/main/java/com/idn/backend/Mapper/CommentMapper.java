package com.idn.backend.Mapper;

import java.util.List;

import org.mapstruct.Mapping;

import com.idn.backend.DTO.CommentRequestDTO;
import com.idn.backend.DTO.CommentResponseDTO;
import com.idn.backend.Model.Comment;

public interface CommentMapper extends BaseMapper {

    Comment toEntity(CommentRequestDTO dto);

    @Mapping(source = "user.userName", target = "username")
    @Mapping(source = "parent.id", target = "parentCommentId")
    CommentResponseDTO toResponseDto(Comment comment);

    List<CommentResponseDTO> toResponseDtoList(List<Comment> comments);

}

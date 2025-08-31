package com.idn.backend.Mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.idn.backend.DTO.UserRequestDTO;
import com.idn.backend.DTO.UserResponseDTO;
import com.idn.backend.Model.UserAuth;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(target = "id", ignore = true)
    UserAuth toEntity(UserRequestDTO dto);

    UserResponseDTO toResponseDTO(UserAuth userAuth);

}

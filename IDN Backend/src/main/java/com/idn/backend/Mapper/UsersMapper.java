package com.idn.backend.Mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.idn.backend.DTO.UsersRequestDTO;
import com.idn.backend.DTO.UsersResponseDTO;
import com.idn.backend.Model.Users;

@Mapper(componentModel = "spring")
public interface UsersMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "role", ignore = true)
    @Mapping(target = "password", ignore = true)
    @Mapping(target = "emailVerified", ignore = true)
    @Mapping(target = "verificationToken", ignore = true)
    @Mapping(target = "tokenExpiry", ignore = true)
    Users toEntity(UsersRequestDTO dto);

    UsersResponseDTO toUsersResponseDTO(Users user);

}

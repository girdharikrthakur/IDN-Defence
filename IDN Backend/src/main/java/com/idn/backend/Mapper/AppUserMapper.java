package com.idn.backend.Mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.idn.backend.DTO.UsersRequestDTO;
import com.idn.backend.DTO.UsersResponseDTO;
import com.idn.backend.Model.AppUser;

public interface AppUserMapper extends BaseMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "role", ignore = true)
    @Mapping(target = "password", ignore = true)
    @Mapping(target = "emailVerified", ignore = true)
    @Mapping(target = "verificationToken", ignore = true)
    @Mapping(target = "tokenExpiry", ignore = true)
    AppUser toEntity(UsersRequestDTO dto);

    UsersResponseDTO toUsersResponseDTO(AppUser user);

}

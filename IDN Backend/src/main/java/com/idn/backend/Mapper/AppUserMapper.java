package com.idn.backend.mapper;

import java.util.List;

import org.mapstruct.Mapper;

import com.idn.backend.dto.response.UsersResponseDTO;
import com.idn.backend.entity.AppUser;

@Mapper(componentModel = "spring")
public interface AppUserMapper {

    UsersResponseDTO toAppUserResponseDTO(AppUser user);

    List<UsersResponseDTO> toAppUserResponseDTOList(List<AppUser> users);

}

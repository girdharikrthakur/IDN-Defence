package com.idn.backend.Mapper;

import java.util.List;

import org.mapstruct.Mapper;

import com.idn.backend.DTO.ResponseDTO.UsersResponseDTO;
import com.idn.backend.Model.AppUser;

@Mapper(componentModel = "spring")
public interface AppUserMapper {

    UsersResponseDTO toAppUserResponseDTO(AppUser user);

    List<UsersResponseDTO> toAppUserResponseDTOList(List<AppUser> users);

}

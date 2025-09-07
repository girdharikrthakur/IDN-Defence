package com.idn.backend.Mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.idn.backend.DTO.AuthResposneDTO;
import com.idn.backend.Model.UserAuth;

@Mapper
public interface AuthMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "role", ignore = true)
    @Mapping(target = "pwd", ignore = true)
    UserAuth toEntity(AuthResposneDTO dto);

    AuthResposneDTO toAuthResponse(UserAuth user);

}

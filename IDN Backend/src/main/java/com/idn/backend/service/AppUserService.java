package com.idn.backend.services;

import java.util.List;

import com.idn.backend.dto.response.UsersResponseDTO;

public interface AppUserService {

    public List<UsersResponseDTO> findAllUsers();

    public UsersResponseDTO findUserById(Long id);

}

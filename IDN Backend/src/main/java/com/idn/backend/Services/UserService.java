package com.idn.backend.Services;

import java.util.Optional;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.idn.backend.DTO.UserRequestDTO;
import com.idn.backend.DTO.UserResponseDTO;
import com.idn.backend.Exception.UserAlreadyExistsException;
import com.idn.backend.Mapper.UserMapper;
import com.idn.backend.Model.UserAuth;
import com.idn.backend.Repo.UserRepo;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class UserService {

    private final UserRepo userRepo;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    public UserResponseDTO saveUser(UserRequestDTO userRequestDTO) {

        Optional<UserAuth> userOption = userRepo.findByEmail(userRequestDTO.getEmail());

        if (userOption.isPresent()) {
            throw new UserAlreadyExistsException("Email already exists: " + userRequestDTO.getEmail());
        }

        userRequestDTO.setPwd(passwordEncoder.encode(userRequestDTO.getPwd()));
        UserAuth userAuth = userMapper.toEntity(userRequestDTO);
        UserAuth savedUserAuth = userRepo.save(userAuth);
        return userMapper.toResponseDTO(savedUserAuth);

    }

}

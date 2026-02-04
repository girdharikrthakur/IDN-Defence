package com.idn.backend.Services;

import java.util.Optional;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.idn.backend.DTO.UsersRequestDTO;
import com.idn.backend.DTO.UsersResponseDTO;
import com.idn.backend.ExceptionHandler.UserAlreadyExistsException;
import com.idn.backend.Mapper.UsersMapper;
import com.idn.backend.Model.Users;
import com.idn.backend.Repo.UsersRepo;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UsersRepo userRepo;
    private final UsersMapper usersMapper;
    private final PasswordEncoder passwordEncoder;

    public UsersResponseDTO saveUser(UsersRequestDTO userRequestDTO) {

        Optional<Users> userOption = userRepo.findByEmail(userRequestDTO.getEmail());

        if (userOption.isPresent()) {
            throw new UserAlreadyExistsException("Email already exists: " + userRequestDTO.getEmail());
        }

        userRequestDTO.setPwd(passwordEncoder.encode(userRequestDTO.getPwd()));
        Users userAuth = usersMapper.toEntity(userRequestDTO);
        Users savedUserAuth = userRepo.save(userAuth);
        return usersMapper.toUsersResponseDTO(savedUserAuth);

    }

}

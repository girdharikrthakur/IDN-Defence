package com.idn.backend.Services;

<<<<<<< HEAD
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.idn.backend.DTO.UserRequestDTO;
import com.idn.backend.DTO.UserResponseDTO;
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

        userRequestDTO.setPwd(passwordEncoder.encode(userRequestDTO.getPwd()));

        UserAuth userAuth = userMapper.toEntity(userRequestDTO);
        UserAuth savedUserAuth = userRepo.save(userAuth);
        return userMapper.toResponseDTO(savedUserAuth);

    }

=======
import org.springframework.stereotype.Service;

@Service
public class UserService {

>>>>>>> 4b67cafe63b444f2d2e9ac432da94992e59320f0
}

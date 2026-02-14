package com.idn.backend.Services;

import java.util.Optional;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.idn.backend.DTO.UsersRequestDTO;
import com.idn.backend.DTO.UsersResponseDTO;
import com.idn.backend.ExceptionHandler.UserAlreadyExistsException;
import com.idn.backend.Mapper.AppUserMapper;
import com.idn.backend.Model.AppUser;
import com.idn.backend.Repo.AppUserRepo;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {

    private final AppUserRepo userRepo;
    private final AppUserMapper usersMapper;
    private final PasswordEncoder passwordEncoder;

    public UsersResponseDTO saveUser(UsersRequestDTO userRequestDTO) {

        Optional<AppUser> userOption = userRepo.findByEmail(userRequestDTO.getEmail());

        if (userOption.isPresent()) {
            throw new UserAlreadyExistsException("Email already exists: " + userRequestDTO.getEmail());
        }

        userRequestDTO.setPwd(passwordEncoder.encode(userRequestDTO.getPwd()));
        AppUser userAuth = usersMapper.toEntity(userRequestDTO);
        AppUser savedUserAuth = userRepo.save(userAuth);
        return usersMapper.toUsersResponseDTO(savedUserAuth);

    }

}

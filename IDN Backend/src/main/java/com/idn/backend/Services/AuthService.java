package com.idn.backend.Services;

import java.util.Optional;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.idn.backend.DTO.UsersResponseDTO;
import com.idn.backend.ExceptionHandler.UserAlreadyExistsException;
import com.idn.backend.Mapper.UsersMapper;
import com.idn.backend.Model.Users;
import com.idn.backend.Repo.UsersRepo;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UsersRepo usersRepo;
    private final UsersMapper usersMapper;
    private final PasswordEncoder passwordEncoder;

    public UsersResponseDTO userSignUp(
            String username,
            String email,
            String password) {

        Optional<Users> fetchedUserByEmail = usersRepo.findByEmail(email);
        Optional<Users> fetchedUserByUsername = usersRepo.findByUserName(username);

        if (fetchedUserByEmail.isPresent() | fetchedUserByUsername.isPresent()) {

            throw new UserAlreadyExistsException(
                    username + "User already Exist , Try Signing in with " + email + "or " + username);
        }

        Users user = new Users();
        user.setUserName(username);
        user.setEmail(email);
        user.setPwd(passwordEncoder.encode(password));
        Users savedUser = usersRepo.save(user);

        return usersMapper.toUsersResponseDTO(savedUser);

    }

}

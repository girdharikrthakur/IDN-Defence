package com.idn.backend.Services;

import java.util.Optional;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.idn.backend.DTO.AuthResposneDTO;
import com.idn.backend.Exception.UserAlreadyExistsException;
import com.idn.backend.Mapper.AuthMapper;
import com.idn.backend.Model.UserAuth;
import com.idn.backend.Repo.AuthRepo;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthRepo authRepo;
    private final AuthMapper authMapper;
    private final PasswordEncoder passwordEncoder;

    public AuthResposneDTO userSignUp(
            String username,
            String email,
            String password) {

        Optional<UserAuth> fetchedUserByEmail = authRepo.findByEmail(email);
        Optional<UserAuth> fetchedUserByUsername = authRepo.findByUserName(username);

        if (fetchedUserByEmail.isPresent() | fetchedUserByUsername.isPresent()) {

            throw new UserAlreadyExistsException(
                    username + "User already Exist , Try Signing in with " + email + "or " + username);
        }

        UserAuth user = new UserAuth();
        user.setUserName(username);
        user.setEmail(email);
        user.setPwd(passwordEncoder.encode(password));
        UserAuth savedUser = authRepo.save(user);

        return authMapper.toAuthResponse(savedUser);

    }

}

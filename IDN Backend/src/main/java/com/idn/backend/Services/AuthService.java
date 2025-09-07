package com.idn.backend.Services;

import org.springframework.stereotype.Service;

import com.idn.backend.DTO.AuthResposneDTO;
import com.idn.backend.Exception.UserAlreadyExistsException;
import com.idn.backend.Mapper.AuthMapper;
import com.idn.backend.Model.UserAuth;
import com.idn.backend.Repo.AuthRepo;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class AuthService {

    private final AuthRepo authRepo;
    private final AuthMapper authMapper;

    public AuthResposneDTO userSignUp(
            String username,
            String email,
            String password) {

        if (authRepo.findByEmail().isPresent() | authRepo.findByUsername().isPresent()) {

            throw new UserAlreadyExistsException(
                    username + "User already Exist , Try Signing in with " + email + "or " + username);
        }
        UserAuth user = new UserAuth();
        user.setUserName(username);
        user.setEmail(email);
        user.setPwd(password);

        return authMapper.toAuthResponse(user);

    }

}

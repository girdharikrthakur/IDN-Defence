package com.idn.backend.Services;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.idn.backend.DTO.RegisterRequest;
import com.idn.backend.ExceptionHandler.UserAlreadyExistsException;
import com.idn.backend.Model.Users;
import com.idn.backend.Repo.UsersRepo;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UsersRepo usersRepo;
    private final EmailService emailService;
    private final PasswordEncoder passwordEncoder;

    public void userSignUp(RegisterRequest request) throws IOException {

        Optional<Users> fetchedUserByEmail = usersRepo.findByEmail(request.getEmail());
        Optional<Users> fetchedUserByUsername = usersRepo.findByUserName(request.getUsername());

        if (fetchedUserByEmail.isPresent() | fetchedUserByUsername.isPresent()) {

            throw new UserAlreadyExistsException(
                    request.getUsername() + "User already Exist , Try Signing in with " + request.getEmail() + "or "
                            + request.getUsername());
        }

        String token = UUID.randomUUID().toString();

        Users user = new Users();
        user.setUserName(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setEmail(request.getEmail());

        user.setRole("user");
        user.setEmailVerified(false);
        user.setVerificationToken(token);
        user.setTokenExpiry(LocalDateTime.now().plusHours(24));

        usersRepo.save(user);

        emailService.sendVerificationEmail(user.getEmail(), token);

    }

}

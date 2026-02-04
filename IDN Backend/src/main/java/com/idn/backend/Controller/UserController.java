package com.idn.backend.Controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.idn.backend.DTO.UsersRequestDTO;
import com.idn.backend.DTO.UsersResponseDTO;
import com.idn.backend.Services.UserService;

import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/public/api/v1/register")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping()
    public ResponseEntity<UsersResponseDTO> saveUser(@RequestBody UsersRequestDTO userRequestDTO) {

        UsersResponseDTO savedUser = userService.saveUser(userRequestDTO);

        return ResponseEntity.ok(savedUser);
    }

}

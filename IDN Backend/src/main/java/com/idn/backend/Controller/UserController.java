package com.idn.backend.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.idn.backend.dto.response.UsersResponseDTO;
import com.idn.backend.services.impl.AppUserServiceImpl;

import lombok.RequiredArgsConstructor;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@RequiredArgsConstructor
@RequestMapping("/private")
public class UserController {

    private final AppUserServiceImpl appUserService;

    @GetMapping("/users")
    public ResponseEntity<List<UsersResponseDTO>> findAllUsers() {

        List<UsersResponseDTO> users = appUserService.findAllUsers();
        return ResponseEntity.ok(users);
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<UsersResponseDTO> findUserById(@PathVariable Long id) {

        UsersResponseDTO user = appUserService.findUserById(id);
        return ResponseEntity.ok(user);
    }

}

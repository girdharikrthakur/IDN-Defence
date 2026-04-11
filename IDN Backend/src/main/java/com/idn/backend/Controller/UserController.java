package com.idn.backend.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.idn.backend.dto.response.UsersResponseDTO;
import com.idn.backend.services.impl.AppUserServiceImpl;

import lombok.RequiredArgsConstructor;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {

    private final AppUserServiceImpl appUserService;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("add")
    public String postMethodName(@RequestBody String entity) {
        // TODO: process POST request

        return entity;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public ResponseEntity<List<UsersResponseDTO>> findAllUsers() {

        List<UsersResponseDTO> users = appUserService.findAllUsers();
        return ResponseEntity.ok(users);
    }

    @PreAuthorize("hasAnyRole('USER','AUTHOR','ADMIN')")
    @GetMapping("/{id}")
    public ResponseEntity<UsersResponseDTO> findUserById(@PathVariable Long id) {

        UsersResponseDTO user = appUserService.findUserById(id);
        return ResponseEntity.ok(user);
    }

}

package com.idn.backend.Controller;

<<<<<<< HEAD
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.idn.backend.DTO.UserRequestDTO;
import com.idn.backend.DTO.UserResponseDTO;
import com.idn.backend.Services.UserService;

import lombok.AllArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/public/api/v1/register")
@AllArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping()
    public ResponseEntity<UserResponseDTO> saveUser(@RequestBody UserRequestDTO userRequestDTO) {

        UserResponseDTO savedUser = userService.saveUser(userRequestDTO);

        return ResponseEntity.ok(savedUser);
    }

=======
public class UserController {
    
>>>>>>> 4b67cafe63b444f2d2e9ac432da94992e59320f0
}

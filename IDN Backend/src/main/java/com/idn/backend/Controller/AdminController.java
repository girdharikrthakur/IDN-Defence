package com.idn.backend.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @PostMapping("/create-user")
    public String createUser() {
        return "User created successfully!";
    }

    @GetMapping("/dashboard")
    public String getAdminDashboard() {
        return "Welcome to the Admin Dashboard!";
    }

}

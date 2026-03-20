package com.idn.backend.controller;

import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/error")
public class ErrorController {

    @GetMapping("/denied")
    public String accessDenied() {
        return "denied.html";
    }

    @GetMapping("/unauthorized")
    public String unauthorized() {
        return "unauthorized.html";
    }

}

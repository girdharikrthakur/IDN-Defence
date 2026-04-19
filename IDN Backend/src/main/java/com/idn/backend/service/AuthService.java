package com.idn.backend.service;

import com.idn.backend.dto.request.RegistrationDTO;

import jakarta.servlet.http.HttpServletRequest;

public interface AuthService {

    public void userSignUp(RegistrationDTO reg, HttpServletRequest request);

}

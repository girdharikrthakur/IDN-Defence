package com.idn.backend.services;

import com.idn.backend.dto.request.RegistrationDTO;

public interface AuthService {

    public void userSignUp(RegistrationDTO reg);

}

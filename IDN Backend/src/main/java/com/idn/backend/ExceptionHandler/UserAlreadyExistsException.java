package com.idn.backend.ExceptionHandler;

import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.http.HttpStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class UserAlreadyExistsException extends RuntimeException {

    // this is comments
    public UserAlreadyExistsException(String message) {
        super(message);
    }
}

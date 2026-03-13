package com.idn.backend.ExceptionHandler;

public class PostNotFoundException extends RuntimeException {

    public PostNotFoundException(String message) {
        super(message);
    }

}

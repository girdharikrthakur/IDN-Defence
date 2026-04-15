package com.idn.backend.service;


import java.io.IOException;

public interface EmailService {

    public void sendVerificationEmail(String toEmail, String token) throws IOException;
}

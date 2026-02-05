package com.idn.backend.Services;

import java.io.IOException;
import org.springframework.stereotype.Service;

import com.google.api.client.util.Value;
import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.SendGrid;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Content;
import com.sendgrid.helpers.mail.objects.Email;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EmailService {

    @Value("${sendgrid.api.key}")
    private String apiKey;

    @Value("${app.verify.url}")
    private String verifyUrl;

    public void sendVerificationEmail(String toEmail, String token) throws IOException {
        SendGrid sendGrid = new SendGrid(apiKey);

        Email from = new Email("girdharikumar101@gmail.com");
        Email to = new Email(toEmail);

        String link = verifyUrl + "?token=" + token;

        Content content = new Content(
                "text/plain",
                "Click the link to verify your email:\n" + link);

        Mail mail = new Mail(from, "Email Verification", to, content);
        Request request = new Request();
        request.setMethod(Method.POST);
        request.setEndpoint("mail/send");
        request.setBody(mail.build());

        sendGrid.api(request);
    }

}

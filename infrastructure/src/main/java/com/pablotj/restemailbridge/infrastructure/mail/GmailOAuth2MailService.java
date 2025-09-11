package com.pablotj.restemailbridge.infrastructure.mail;

import com.pablotj.restemailbridge.domain.model.Email;
import com.pablotj.restemailbridge.domain.service.EmailService;
import org.springframework.stereotype.Component;

@Component
public class GmailOAuth2MailService implements EmailService {

    @Override
    public Email sendEmail(Email email) {
        System.out.println("Sending email " + email.getSubject() + " to " + email.getTo());
        return email;
    }
}

package com.pablotj.restemailbridge.domain.service;

import com.pablotj.restemailbridge.domain.model.Email;


public class EmailValidatorService {

    /**
     * Validates business rules for Email.
     */
    public void validate(Email email) {
        if (email == null) throw new IllegalArgumentException("Email cannot be null");
        if (email.getTo() == null || !email.getTo().matches(".+@.+\\..+"))
            throw new IllegalArgumentException("Recipient email is invalid");
        if (email.getFrom() == null || email.getFrom().isBlank())
            throw new IllegalArgumentException("Sender email is required");
        if (email.getSubject() == null || email.getSubject().isBlank())
            throw new IllegalArgumentException("Subject is required");
        if (email.getBody() == null || email.getBody().isBlank())
            throw new IllegalArgumentException("Body is required");
    }
}

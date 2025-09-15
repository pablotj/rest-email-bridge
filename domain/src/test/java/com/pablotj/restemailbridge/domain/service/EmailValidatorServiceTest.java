package com.pablotj.restemailbridge.domain.service;

import com.pablotj.restemailbridge.domain.model.Email;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

class EmailValidatorServiceTest {

    private final EmailValidatorService validator = new EmailValidatorService();

    @Test
    void shouldThrowIfEmailIsNull() {
        assertThatThrownBy(() -> validator.validate(null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Email cannot be null");
    }

    @Test
    void shouldThrowIfRecipientInvalid() {
        Email email = Email.create("sender@example.com", "not-an-email", "Subject", "Body");

        assertThatThrownBy(() -> validator.validate(email))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Recipient email is invalid");
    }

    @Test
    void shouldThrowIfSenderIsBlank() {
        Email email = Email.create("", "recipient@example.com", "Subject", "Body");

        assertThatThrownBy(() -> validator.validate(email))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Sender email is required");
    }

    @Test
    void shouldThrowIfSubjectIsBlank() {
        Email email = Email.create("sender@example.com", "recipient@example.com", "", "Body");

        assertThatThrownBy(() -> validator.validate(email))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Subject is required");
    }

    @Test
    void shouldThrowIfBodyIsBlank() {
        Email email = Email.create("sender@example.com", "recipient@example.com", "Subject", "");

        assertThatThrownBy(() -> validator.validate(email))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Body is required");
    }

    @Test
    void shouldPassForValidEmail() {
        Email email = Email.create("sender@example.com", "recipient@example.com", "Subject", "Body");

        // no debe lanzar excepción
        validator.validate(email);
    }
}
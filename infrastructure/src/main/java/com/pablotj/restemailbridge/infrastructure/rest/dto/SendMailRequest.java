package com.pablotj.restemailbridge.infrastructure.rest.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.Length;

@Schema(description = "Request payload to send an email")
public record SendMailRequest(
        @NotBlank(message = "{email.from.blank}")
        @Email(message = "{email.from.invalid}")
        @Length(min = 4, max = 100, message = "email.from.length}")
        @Schema(description = "Sender email address", example = "user@example.com")
        String from,

        @NotBlank(message = "{email.subject.blank}")
        @Length(min=1, max = 30, message = "{email.subject.length}")
        @Schema(description = "Email subject", example = "Welcome to RestEmailBridge")
        String subject,

        @NotBlank(message = "{email.body.blank}")
        @Length(min=1, max = 4000, message = "{email.body.length}")
        @Schema(description = "Email body content", example = "Hello, thanks for signing up!")
        String body
) {
}
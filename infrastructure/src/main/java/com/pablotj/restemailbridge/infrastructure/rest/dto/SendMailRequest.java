package com.pablotj.restemailbridge.infrastructure.rest.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.Length;

public record SendMailRequest(
        @NotBlank @Email @Length(min = 4, max = 100) String from,
        @NotBlank @Length(min=1, max = 30) String subject,
        @NotBlank @Length(min=1, max = 4000) String body
) {
}
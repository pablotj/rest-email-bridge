package com.pablotj.restemailbridge.infrastructure.rest.dto;

public record SendMailRequest(
        String from,
        String subject,
        String body
) {
}

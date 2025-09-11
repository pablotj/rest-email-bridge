package com.pablotj.restemailbridge.application.dto;

public record EmailDTO(
        String from,
        String subject,
        String body
) {
}

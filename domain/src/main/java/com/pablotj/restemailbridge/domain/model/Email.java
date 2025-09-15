package com.pablotj.restemailbridge.domain.model;

import java.time.Instant;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class Email {

    private final String from;
    private final String to;
    private final String subject;
    private final String body;
    private EmailStatus status;
    private final Instant createdAt;
    private String errorDescription;

    public void markAsSent() {
        this.status = EmailStatus.SENT;
    }

    public void markAsFailed(String errorDescription) {
        this.status = EmailStatus.FAILED;
        this.errorDescription = errorDescription;
    }

    public static Email create(String from, String to, String subject, String body) {
        return Email.builder()
                .from(from)
                .to(to)
                .subject(subject)
                .body(body)
                .status(EmailStatus.PENDING)
                .build();
    }
}
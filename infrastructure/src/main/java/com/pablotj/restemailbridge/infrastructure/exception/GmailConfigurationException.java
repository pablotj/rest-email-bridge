package com.pablotj.restemailbridge.infrastructure.exception;

import java.io.Serial;

public class GmailConfigurationException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = 1L;

    public GmailConfigurationException(String message) {
        super(message);
    }
}

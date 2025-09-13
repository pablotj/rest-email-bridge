package com.pablotj.restemailbridge.infrastructure.exception;

import java.io.Serial;

public class GmailInitializationException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = 1L;

    public GmailInitializationException(String message, Throwable cause) {
        super(message, cause);
    }
}

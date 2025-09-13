package com.pablotj.restemailbridge.infrastructure.exception;

import java.io.Serial;

public class GmailSendErrorException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = 1L;

    public GmailSendErrorException(String message, Throwable cause) {
        super(message, cause);
    }

}

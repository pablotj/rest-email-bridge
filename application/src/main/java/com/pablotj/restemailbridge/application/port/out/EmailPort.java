package com.pablotj.restemailbridge.application.port.out;

import com.pablotj.restemailbridge.domain.model.Email;

public interface EmailPort {

    Email sendEmail(Email email);
}

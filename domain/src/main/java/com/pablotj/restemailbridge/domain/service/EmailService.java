package com.pablotj.restemailbridge.domain.service;

import com.pablotj.restemailbridge.domain.model.Email;

public interface EmailService {

    Email sendEmail(Email email);
}

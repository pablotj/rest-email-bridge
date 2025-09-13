package com.pablotj.restemailbridge.infrastructure.mail;

import com.pablotj.restemailbridge.application.port.in.EmailDefaultConfigPort;
import org.springframework.stereotype.Component;

@Component
public class GmailDefaultConfigAdapter implements EmailDefaultConfigPort {
    @Override
    public String getDefaultRecipient() {
        return "pablodelatorree@gmail.com";
    }
}
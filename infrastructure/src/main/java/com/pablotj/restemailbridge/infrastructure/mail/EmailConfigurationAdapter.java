package com.pablotj.restemailbridge.infrastructure.mail;

import com.pablotj.restemailbridge.application.port.EmailConfigurationPort;
import org.springframework.stereotype.Component;

@Component
public class EmailConfigurationAdapter implements EmailConfigurationPort {
    @Override
    public String getDefaultRecipient() {
        return "pablodelatorree@gmail.com";
    }
}
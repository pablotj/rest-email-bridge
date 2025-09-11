package com.pablotj.restemailbridge.infrastructure.config;

import com.pablotj.restemailbridge.application.port.EmailConfigurationPort;
import org.springframework.stereotype.Component;

@Component
public class EmailConfigurationAdapter implements EmailConfigurationPort {
    @Override
    public String getDefaultRecipient() {
        return "1234@1234.com";
    }
}
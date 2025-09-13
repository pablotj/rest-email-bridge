package com.pablotj.restemailbridge.infrastructure.mail.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "gmail.oauth2")
public record GmailOAuth2Properties(
        String clientId,
        String clientSecret,
        String redirectUri
) {}

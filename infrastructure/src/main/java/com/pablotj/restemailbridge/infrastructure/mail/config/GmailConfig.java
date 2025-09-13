package com.pablotj.restemailbridge.infrastructure.mail.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(GmailOAuth2Properties.class)
public class GmailConfig {
}

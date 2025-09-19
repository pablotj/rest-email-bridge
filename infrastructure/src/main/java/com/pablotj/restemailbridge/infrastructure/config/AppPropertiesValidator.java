package com.pablotj.restemailbridge.infrastructure.config;

import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class AppPropertiesValidator {

    private static final Logger log = LoggerFactory.getLogger(AppPropertiesValidator.class);

    @Value("${app.encryption.secret:}")
    private String encryptionSecret;

    @Value("${app.cors.allowed-origins:}")
    private String allowedOrigins;

    @Value("${spring.datasource.url:}")
    private String datasourceUrl;

    @Value("${spring.datasource.username:}")
    private String datasourceUser;

    @Value("${spring.datasource.password:}")
    private String datasourcePassword;

    @Value("${gmail.oauth2.clientId:}")
    private String gmailClientId;

    @Value("${gmail.oauth2.clientSecret:}")
    private String gmailClientSecret;

    @PostConstruct
    public void validate() {
        check("APP_ENCRYPTION_SECRET", encryptionSecret, false);
        check("APP_ALLOWED_ORIGINS", allowedOrigins, true);
        check("DB_URL", datasourceUrl, true);
        check("DB_USER", datasourceUser, true);
        check("DB_PASSWORD", datasourcePassword, false);
        check("GMAIL_OAUTH_CLIENT_ID", gmailClientId, true);
        check("GMAIL_OAUTH_CLIENT_SECRET", gmailClientSecret, false);
    }

    private void check(String name, String value, boolean logValue) {
        if (value == null || value.isEmpty()) {
            throw new IllegalStateException("ERROR: Property '" + name + "' is not defined or empty");
        } else if (logValue) {
            log.info("Property '{}' is defined with value: {}", name, value);
        } else {
            log.info("Property '{}' is defined", name);
        }
    }
}
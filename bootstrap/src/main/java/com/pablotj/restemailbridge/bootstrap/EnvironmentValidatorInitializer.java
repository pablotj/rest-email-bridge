package com.pablotj.restemailbridge.bootstrap;

import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;

public class EnvironmentValidatorInitializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {

    @Override
    public void initialize(ConfigurableApplicationContext ctx) {
        String[] requiredProperties = {
                "APP_ENCRYPTION_SECRET",
                "APP_ALLOWED_ORIGINS",
                "DB_NAME",
                "DB_USER",
                "DB_PASSWORD",
                "DB_HOST",
                "DB_PORT",
                "GMAIL_OAUTH_CLIENT_ID",
                "GMAIL_OAUTH_CLIENT_SECRET"
        };

        for (String prop : requiredProperties) {
            String value = ctx.getEnvironment().getProperty(prop);
            if (value == null || value.isEmpty()) {
                throw new IllegalStateException("ERROR: Property '" + prop + "' is not defined or empty");
            }
        }
    }
}

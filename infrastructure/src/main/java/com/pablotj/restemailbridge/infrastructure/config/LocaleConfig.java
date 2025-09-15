package com.pablotj.restemailbridge.infrastructure.config;

import java.util.List;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.i18n.AcceptHeaderLocaleResolver;

import java.util.Locale;

@Configuration
public class LocaleConfig {

    @Bean
    public LocaleResolver localeResolver() {
        AcceptHeaderLocaleResolver resolver = new AcceptHeaderLocaleResolver();
        resolver.setDefaultLocale(Locale.of("en"));
        resolver.setSupportedLocales(
                List.of(Locale.of("es"),
                        Locale.of("en"),
                        Locale.of("gl"))
        );
        return resolver;
    }
}

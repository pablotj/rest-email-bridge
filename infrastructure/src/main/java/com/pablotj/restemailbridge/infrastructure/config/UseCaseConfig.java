package com.pablotj.restemailbridge.infrastructure.config;

import com.pablotj.restemailbridge.application.port.EmailConfigurationPort;
import com.pablotj.restemailbridge.application.usecase.SendEmailUseCase;
import com.pablotj.restemailbridge.domain.repository.EmailRepository;
import com.pablotj.restemailbridge.domain.service.EmailService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UseCaseConfig {

    @Bean
    public SendEmailUseCase sendEmailUseCase(EmailConfigurationPort emailConfigurationPort, EmailService emailService, EmailRepository emailRepository) {
        return new SendEmailUseCase(emailConfigurationPort, emailService, emailRepository);
    }
}
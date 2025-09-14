package com.pablotj.restemailbridge.infrastructure.config;

import com.pablotj.restemailbridge.application.port.in.EmailDefaultConfigPort;
import com.pablotj.restemailbridge.application.port.out.EmailPort;
import com.pablotj.restemailbridge.application.usecase.SendEmailUseCase;
import com.pablotj.restemailbridge.domain.repository.EmailRepository;
import com.pablotj.restemailbridge.domain.service.EmailValidatorService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UseCaseConfig {

    @Bean
    public SendEmailUseCase sendEmailUseCase(EmailDefaultConfigPort emailConfigurationPort, EmailPort emailService, EmailRepository emailRepository) {
        return new SendEmailUseCase(new EmailValidatorService(), emailConfigurationPort, emailService, emailRepository);
    }
}
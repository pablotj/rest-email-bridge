package com.pablotj.restemailbridge.application.usecase;

import com.pablotj.restemailbridge.application.dto.EmailDTO;
import com.pablotj.restemailbridge.application.port.EmailConfigurationPort;
import com.pablotj.restemailbridge.domain.model.Email;
import com.pablotj.restemailbridge.domain.repository.EmailRepository;
import com.pablotj.restemailbridge.domain.service.EmailService;

public class SendEmailUseCase {
    private final EmailConfigurationPort emailConfigurationPort;
    private final EmailService emailService;
    private final EmailRepository emailRepository;

    public SendEmailUseCase(EmailConfigurationPort emailConfigurationPort, EmailService emailService, EmailRepository emailRepository) {
        this.emailConfigurationPort = emailConfigurationPort;
        this.emailService = emailService;
        this.emailRepository = emailRepository;
    }

    public void handle(EmailDTO emailDTO) {
        String to = emailConfigurationPort.getDefaultRecipient();
        Email email = emailService.sendEmail(
                Email.builder()
                        .from(emailDTO.from())
                        .to(to)
                        .subject(emailDTO.subject())
                        .body(emailDTO.body())
                        .build()
        );
        emailRepository.save(email);
    }
}

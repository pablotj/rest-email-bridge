package com.pablotj.restemailbridge.application.usecase;

import com.pablotj.restemailbridge.application.dto.EmailDTO;
import com.pablotj.restemailbridge.application.port.in.EmailDefaultConfigPort;
import com.pablotj.restemailbridge.application.port.out.EmailPort;
import com.pablotj.restemailbridge.domain.model.Email;
import com.pablotj.restemailbridge.domain.repository.EmailRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Use case for sending emails.
 * <p>
 * Retrieves the default recipient from EmailDefaultConfigPort, sends the email using EmailPort,
 * and persists it via EmailRepository.
 */
public class SendEmailUseCase {

    private static final Logger log = LoggerFactory.getLogger(SendEmailUseCase.class);

    private final EmailDefaultConfigPort emailConfigurationPort;
    private final EmailPort emailService;
    private final EmailRepository emailRepository;

    /**
     * Constructor injecting required ports.
     *
     * @param emailConfigurationPort Port to retrieve configuration
     * @param emailService           Service to send emails
     * @param emailRepository        Repository to persist emails
     */
    public SendEmailUseCase(EmailDefaultConfigPort emailConfigurationPort,
                            EmailPort emailService,
                            EmailRepository emailRepository) {
        this.emailConfigurationPort = emailConfigurationPort;
        this.emailService = emailService;
        this.emailRepository = emailRepository;
    }

    /**
     * Handles sending an email based on the provided DTO.
     *
     * @param emailDTO DTO containing from, subject, and body
     */
    public void handle(EmailDTO emailDTO) {
        String to = emailConfigurationPort.getDefaultRecipient();
        log.info("Sending email from {} to {}", emailDTO.from(), to);

        Email email = emailService.sendEmail(
                Email.builder()
                        .from(emailDTO.from())
                        .to(to)
                        .subject(emailDTO.subject())
                        .body(emailDTO.body())
                        .build()
        );

        emailRepository.save(email);
        log.info("Email successfully sent and persisted to repository for recipient {}", to);
    }
}
package com.pablotj.restemailbridge.application.usecase;

import com.pablotj.restemailbridge.application.dto.EmailDTO;
import com.pablotj.restemailbridge.application.port.in.EmailDefaultConfigPort;
import com.pablotj.restemailbridge.application.port.out.EmailPort;
import com.pablotj.restemailbridge.domain.model.Email;
import com.pablotj.restemailbridge.domain.model.EmailStatus;
import com.pablotj.restemailbridge.domain.repository.EmailRepository;
import com.pablotj.restemailbridge.domain.service.EmailValidatorService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class SendEmailUseCaseTest {

    private EmailValidatorService emailValidatorService;
    private EmailPort emailPort;
    private EmailRepository emailRepository;

    private SendEmailUseCase useCase;

    @BeforeEach
    void setUp() {
        EmailDefaultConfigPort emailDefaultConfigPort = mock(EmailDefaultConfigPort.class);

        emailValidatorService = mock(EmailValidatorService.class);
        emailPort = mock(EmailPort.class);
        emailRepository = mock(EmailRepository.class);

        useCase = new SendEmailUseCase(
                emailValidatorService,
                emailDefaultConfigPort,
                emailPort,
                emailRepository
        );

        when(emailDefaultConfigPort.getDefaultRecipient()).thenReturn("default@example.com");
    }

    @Test
    void shouldSendEmailSuccessfully() {
        // given
        EmailDTO dto = new EmailDTO("sender@example.com", "Subject", "Body");
        when(emailPort.sendEmail(any(Email.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        // when
        useCase.handle(dto);

        // then
        verify(emailValidatorService).validate(any(Email.class));
        verify(emailPort).sendEmail(any(Email.class));
        verify(emailRepository).save(argThat(email ->
                email.getStatus() == EmailStatus.SENT
        ));
    }

    @Test
    void shouldMarkEmailAsFailedWhenSendThrowsException() {
        // given
        EmailDTO dto = new EmailDTO("sender@example.com", "Subject", "Body");
        when(emailPort.sendEmail(any(Email.class))).thenThrow(new RuntimeException("SMTP error"));

        // when
        useCase.handle(dto);

        // then
        verify(emailValidatorService).validate(any(Email.class));
        verify(emailRepository).save(argThat(email ->
                email.getStatus() == EmailStatus.FAILED &&
                        email.getErrorDescription().contains("SMTP error")
        ));
    }

    @Test
    void shouldUseDefaultRecipientFromConfigPort() {
        // given
        EmailDTO dto = new EmailDTO("sender@example.com", "Subject", "Body");

        when(emailPort.sendEmail(any(Email.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        // when
        useCase.handle(dto);

        // then
        verify(emailRepository).save(argThat(email ->
                email.getTo().equals("default@example.com")
        ));
    }

    @Test
    void shouldPropagateExceptionWhenValidatorFails() {
        // given
        EmailDTO dto = new EmailDTO("sender@example.com", "Subject", "Body");

        doThrow(new RuntimeException("Invalid email")).when(emailValidatorService).validate(any());

        // when & then
        RuntimeException ex = assertThrows(RuntimeException.class, () -> useCase.handle(dto));
        assertThat(ex.getMessage()).isEqualTo("Invalid email");

        // El repositorio no debería guardar el email, porque la validación falló antes
        verify(emailRepository, never()).save(any());
    }

    @Test
    void shouldFailWhenEmailDTOHasNullFields() {
        // given
        EmailDTO dto = new EmailDTO(null, null, null);

        doThrow(new IllegalArgumentException("Invalid fields")).when(emailValidatorService).validate(any());

        // when & then
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> useCase.handle(dto));
        assertThat(ex.getMessage()).isEqualTo("Invalid fields");

        verify(emailRepository, never()).save(any());
    }
}
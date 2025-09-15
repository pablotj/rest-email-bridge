package com.pablotj.restemailbridge.infrastructure.persistence;

import com.pablotj.restemailbridge.domain.model.Email;
import com.pablotj.restemailbridge.domain.model.EmailStatus;
import com.pablotj.restemailbridge.infrastructure.config.JpaConfig;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import static org.assertj.core.api.Assertions.assertThat;

@Import(JpaConfig.class)
@DataJpaTest
class MailRepositoryAdapterIT {

    @Autowired
    private SpringDataMailRepository springDataMailRepository;

    @Test
    void shouldSaveEmailSuccessfully() {
        MailRepositoryAdapter adapter = new MailRepositoryAdapter(springDataMailRepository);

        Email email = Email.create("sender@example.com", "recipient@example.com", "Subject", "Body");

        Email saved = adapter.save(email);

        assertThat(saved.getFrom()).isEqualTo("sender@example.com");
        assertThat(saved.getTo()).isEqualTo("recipient@example.com");
        assertThat(saved.getSubject()).isEqualTo("Subject");
        assertThat(saved.getBody()).isEqualTo("Body");
        assertThat(saved.getStatus()).isEqualTo(EmailStatus.PENDING);

        assertThat(springDataMailRepository.findAll())
                .hasSize(1)
                .first()
                .extracting("sender", "recipient")
                .containsExactly("sender@example.com", "recipient@example.com");
    }
}
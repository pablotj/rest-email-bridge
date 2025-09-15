package com.pablotj.restemailbridge.infrastructure.persistence;

import com.pablotj.restemailbridge.domain.model.Email;
import com.pablotj.restemailbridge.domain.repository.EmailRepository;
import org.springframework.stereotype.Repository;

@Repository
public class MailRepositoryAdapter implements EmailRepository {

    private final SpringDataMailRepository springDataMailRepository;

    public MailRepositoryAdapter(SpringDataMailRepository springDataMailRepository) {
        this.springDataMailRepository = springDataMailRepository;
    }

    @Override
    public Email save(Email email) {
        MailJpa mailJpa = new MailJpa();
        mailJpa.setSender(email.getFrom());
        mailJpa.setRecipient(email.getTo());
        mailJpa.setSubjet(email.getSubject());
        mailJpa.setBody(email.getBody());
        mailJpa.setStatus(email.getStatus());
        mailJpa.setErrorDescription(email.getErrorDescription());
        springDataMailRepository.save(mailJpa);

        return email;
    }
}

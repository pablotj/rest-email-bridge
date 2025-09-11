package com.pablotj.restemailbridge.infrastructure.persistence;

import org.springframework.data.jpa.repository.JpaRepository;


public interface SpringDataMailRepository extends JpaRepository<MailJpa, Long> {
}

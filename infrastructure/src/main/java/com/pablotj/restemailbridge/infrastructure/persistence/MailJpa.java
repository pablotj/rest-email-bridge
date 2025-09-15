package com.pablotj.restemailbridge.infrastructure.persistence;

import com.pablotj.restemailbridge.domain.model.EmailStatus;
import com.pablotj.restemailbridge.infrastructure.encryption.EncryptionConverter;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.Instant;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "MAIL")
@Getter
@Setter
public class MailJpa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 200, nullable = false)
    @Convert(converter = EncryptionConverter.class)
    private String sender;

    @Column(length = 200, nullable = false)
    private String recipient;

    @Column(length = 150, nullable = false)
    @Convert(converter = EncryptionConverter.class)
    private String subjet;

    @Column(length = 7000, nullable = false)
    @Convert(converter = EncryptionConverter.class)
    private String body;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private EmailStatus status;

    @CreatedDate
    @Column(nullable = false)
    private Instant createdAt;

    @Column
    private String errorDescription;
}

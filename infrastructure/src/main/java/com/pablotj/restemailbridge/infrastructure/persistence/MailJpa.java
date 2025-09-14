package com.pablotj.restemailbridge.infrastructure.persistence;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "MAIL")
@Getter
@Setter
public class MailJpa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 100, nullable = false)
    private String sender;

    @Column(length = 100, nullable = false)
    private String recipient;

    @Column(length = 50, nullable = false)
    private String subjet;

    @Column(length = 40000, nullable = false)
    private String body;
}

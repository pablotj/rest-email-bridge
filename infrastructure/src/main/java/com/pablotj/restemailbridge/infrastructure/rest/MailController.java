package com.pablotj.restemailbridge.infrastructure.rest;

import com.pablotj.restemailbridge.application.dto.EmailDTO;
import com.pablotj.restemailbridge.application.usecase.SendEmailUseCase;
import com.pablotj.restemailbridge.infrastructure.rest.dto.SendMailRequest;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/mail")
public class MailController {

    private final SendEmailUseCase sendEmailUseCase;

    public MailController(SendEmailUseCase sendEmailUseCase) {
        this.sendEmailUseCase = sendEmailUseCase;
    }


    @PostMapping
    public ResponseEntity<Void> send(@Valid @RequestBody SendMailRequest request) {
        sendEmailUseCase.handle(new EmailDTO(request.from(), request.subject(), request.body()));
        return ResponseEntity.ok().build();
    }
}
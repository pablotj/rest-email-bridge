package com.pablotj.restemailbridge.infrastructure.rest;

import com.pablotj.restemailbridge.application.dto.EmailDTO;
import com.pablotj.restemailbridge.application.usecase.SendEmailUseCase;
import com.pablotj.restemailbridge.infrastructure.rest.dto.SendMailRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST controller responsible for handling email-related requests.
 * <p>
 * Exposes endpoints under {@code /v1/mail} to send emails through the system.
 * Delegates business logic to the {@link SendEmailUseCase}.
 */
@RestController
@RequestMapping("/v1/mail")
@Tag(name = "Mail API", description = "Endpoints for sending emails")
public class MailController {

    private final SendEmailUseCase sendEmailUseCase;

    /**
     * Creates a new {@link MailController} instance.
     *
     * @param sendEmailUseCase the use case responsible for sending emails
     */
    public MailController(SendEmailUseCase sendEmailUseCase) {
        this.sendEmailUseCase = sendEmailUseCase;
    }

    /**
     * Sends a new email using the provided request data.
     * <p>
     * The request payload is validated using {@link jakarta.validation.Valid}.
     *
     * @param request the email request containing sender, subject and body
     * @return {@link ResponseEntity} with HTTP 200 (OK) if the email is sent successfully
     */
    @PostMapping
    @Operation(
            summary = "Send an email",
            description = "Sends an email using the provided sender, subject, and body.",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    required = true,
                    content =  @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(
                                    name = "Basic email",
                                    value = "{ \"from\": \"user@example.com\", \"subject\": \"Hello\", \"body\": \"Hi there!\" }"
                            )
                    )
            )
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Email sent successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request payload"),
            @ApiResponse(
                    responseCode = "401",
                    description = "Unauthorized – missing or invalid authentication token",
                    content = @Content(schema = @Schema(hidden = true))
            ),
            @ApiResponse(
                    responseCode = "403",
                    description = "Forbidden – the authenticated user cannot send to the specified recipient",
                    content = @Content(schema = @Schema(hidden = true))
            ),
            @ApiResponse(
                    responseCode = "422",
                    description = "Unprocessable Entity – domain validation failed (e.g. invalid email address, business rule violation)",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(
                                    example = "{ \"error\": \"Invalid recipient domain\" }"
                            )
                    )
            ),
            @ApiResponse(responseCode = "500", description = "Unexpected server error")
    })
    public ResponseEntity<Void> send(@Valid @RequestBody SendMailRequest request) {
        sendEmailUseCase.handle(new EmailDTO(request.from(), request.subject(), request.body()));
        return ResponseEntity.ok().build();
    }
}
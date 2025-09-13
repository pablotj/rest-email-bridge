package com.pablotj.restemailbridge.infrastructure.mail;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.gmail.Gmail;
import com.google.api.services.gmail.GmailScopes;
import com.google.api.services.gmail.model.Message;
import com.pablotj.restemailbridge.domain.model.Email;
import com.pablotj.restemailbridge.domain.service.EmailService;
import com.pablotj.restemailbridge.infrastructure.exception.GmailConfigurationException;
import com.pablotj.restemailbridge.infrastructure.exception.GmailInitializationException;
import com.pablotj.restemailbridge.infrastructure.exception.GmailSendErrorException;
import com.pablotj.restemailbridge.infrastructure.mail.config.GmailOAuth2Properties;
import jakarta.annotation.PostConstruct;
import jakarta.mail.MessagingException;
import jakarta.mail.Session;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.Base64;
import java.util.Collections;
import java.util.List;
import java.util.Properties;

/**
 * Gmail implementation of the EmailService using OAuth2 authentication.
 * <p>
 * Handles sending emails via Gmail API and manages credentials stored in a local token folder.
 */
@Component
public class GmailOAuth2MailService implements EmailService {

    private static final Logger log = LoggerFactory.getLogger(GmailOAuth2MailService.class);
    private static final String APPLICATION_NAME = "MailServiceApi";
    private static final JsonFactory JSON_FACTORY = GsonFactory.getDefaultInstance();
    private static final List<String> SCOPES = Collections.singletonList(GmailScopes.GMAIL_SEND);
    private static final String TOKENS_DIRECTORY_PATH = "/app/tokens";

    private final GmailOAuth2Properties properties;
    private Gmail service;

    /**
     * Constructor injecting Gmail OAuth2 properties.
     *
     * @param properties OAuth2 configuration for Gmail
     */
    public GmailOAuth2MailService(GmailOAuth2Properties properties) {
        this.properties = properties;
    }

    /**
     * Initializes the Gmail client with OAuth2 credentials.
     * <p>
     * Loads the token from the token folder, sets up the OAuth2 flow,
     * and initializes the Gmail API client.
     *
     * @throws GmailConfigurationException if token folder or credentials are missing
     * @throws GmailInitializationException if any other initialization error occurs
     */
    @PostConstruct
    public void init() {
        try {
            log.info("Initializing Gmail OAuth2 client...");

            GoogleClientSecrets clientSecrets = new GoogleClientSecrets()
                    .setInstalled(
                            new GoogleClientSecrets.Details()
                                    .setClientId(properties.clientId())
                                    .setClientSecret(properties.clientSecret())
                                    .setRedirectUris(Collections.singletonList(properties.redirectUri()))
                    );

            File tokenFolder = new File(TOKENS_DIRECTORY_PATH);
            if (!tokenFolder.exists() || !tokenFolder.isDirectory()) {
                log.error("Token folder not found: {}", TOKENS_DIRECTORY_PATH);
                throw new GmailConfigurationException("Token folder missing: " + TOKENS_DIRECTORY_PATH);
            }

            GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
                    GoogleNetHttpTransport.newTrustedTransport(),
                    JSON_FACTORY,
                    clientSecrets,
                    SCOPES)
                    .setDataStoreFactory(new FileDataStoreFactory(tokenFolder))
                    .setAccessType("offline")
                    .build();

            LocalServerReceiver receiver = new LocalServerReceiver.Builder().setPort(8888).build();
            Credential credential = new AuthorizationCodeInstalledApp(flow, receiver).authorize("user");
            if (credential == null) {
                log.error("No stored credentials found. Generate tokens first.");
                throw new GmailConfigurationException("No stored credentials found. Generate tokens first.");
            }

            service = new Gmail.Builder(GoogleNetHttpTransport.newTrustedTransport(), JSON_FACTORY, credential)
                    .setApplicationName(APPLICATION_NAME)
                    .build();

            log.info("Gmail OAuth2 client initialized successfully.");

        } catch (Exception e) {
            if (e instanceof GmailConfigurationException configEx) {
                throw configEx;
            }
            log.error("Failed to initialize Gmail client");
            throw new GmailInitializationException("Failed to initialize Gmail client", e);
        }
    }

    /**
     * Sends an email using the Gmail API.
     *
     * @param email Email object containing recipient, sender, subject, and body
     * @return The same Email object after sending
     * @throws GmailSendErrorException if sending fails
     */
    @Override
    public Email sendEmail(Email email) {
        try {
            log.info("Sending email to {}", email.getTo());
            MimeMessage message = createEmail(email.getTo(), email.getFrom(), email.getSubject(), email.getBody());
            sendMessage(service, message);
            log.info("Email sent successfully to {}", email.getTo());
        } catch (Exception e) {
            log.error("Failed to send email to {}", email.getTo());
            throw new GmailSendErrorException("Failed to send email", e);
        }
        return email;
    }

    /**
     * Creates a MimeMessage from the given parameters.
     *
     * @param to       Recipient email
     * @param from     Sender email
     * @param subject  Email subject
     * @param bodyText Email body
     * @return MimeMessage ready to be sent
     * @throws MessagingException if creation fails
     */
    private static MimeMessage createEmail(String to, String from, String subject, String bodyText) throws MessagingException {
        Properties props = new Properties();
        Session session = Session.getDefaultInstance(props, null);
        MimeMessage email = new MimeMessage(session);
        email.setReplyTo(new jakarta.mail.Address[]{new InternetAddress(from)});
        email.setFrom(new InternetAddress(from));
        email.addRecipient(jakarta.mail.Message.RecipientType.TO, new InternetAddress(to));
        email.setSubject(subject);
        email.setText(bodyText);
        return email;
    }

    /**
     * Sends the MimeMessage using the Gmail API.
     *
     * @param service Gmail client
     * @param email   MimeMessage to send
     * @throws MessagingException if message creation fails
     * @throws IOException        if API call fails
     */
    private static void sendMessage(Gmail service, MimeMessage email) throws MessagingException, IOException {
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        email.writeTo(buffer);
        String encodedEmail = Base64.getUrlEncoder().encodeToString(buffer.toByteArray());
        Message message = new Message();
        message.setRaw(encodedEmail);
        service.users().messages().send("me", message).execute();
    }
}
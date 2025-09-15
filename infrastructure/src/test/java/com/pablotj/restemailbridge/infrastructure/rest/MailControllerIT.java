package com.pablotj.restemailbridge.infrastructure.rest;

import com.pablotj.restemailbridge.application.port.out.EmailPort;
import com.pablotj.restemailbridge.infrastructure.RestEmailBridgeTestApplication;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(
        classes = RestEmailBridgeTestApplication.class,
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
@AutoConfigureMockMvc
@ActiveProfiles("test")
class MailControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EmailPort emailPort;

    @Test
    void shouldReturn200WhenEmailIsSent() throws Exception {
        when(emailPort.sendEmail(any())).thenAnswer(invocation -> invocation.getArgument(0));

        mockMvc.perform(post("/v1/mail")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"from\":\"sender@example.com\",\"subject\":\"Subject\",\"body\":\"Body\"}"))
                .andExpect(status().isOk());

        verify(emailPort).sendEmail(any());
    }

    @Test
    void shouldReturn400WhenRequestIsInvalid() throws Exception {
        mockMvc.perform(post("/v1/mail")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"subject\":\"Subject\",\"body\":\"Body\"}"))
                .andExpect(status().isBadRequest());
    }
}
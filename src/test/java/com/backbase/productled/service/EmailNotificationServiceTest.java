package com.backbase.productled.service;

import com.backbase.email.integration.rest.spec.v2.email.EmailPostRequestBody;
import com.backbase.outbound.integration.communications.rest.spec.v1.model.Content;
import com.backbase.outbound.integration.communications.rest.spec.v1.model.Recipient;
import com.backbase.productled.config.DefaultMailSenderProperties;
import com.backbase.productled.mapper.EmailPostRequestBodyMapper;
import com.backbase.productled.testutils.BatchResponseFactory;
import com.backbase.productled.testutils.EmailRequestFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.test.context.junit4.SpringRunner;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
@RunWith(SpringRunner.class)
public class EmailNotificationServiceTest {

    static {
        System.setProperty("SIG_SECRET_KEY", "JWTSecretKeyDontUseInProduction!");
    }

    @Autowired
    EmailNotificationService emailNotificationService;
    @Autowired
    JavaMailSender javaMailSender;
    @Autowired
    DefaultMailSenderProperties defaultMailSenderProperties;
    @MockBean
    EmailPostRequestBodyMapper emailRequestMapper;

    @Test
    public void processRecipient(){
        final Recipient recipient = BatchResponseFactory.batchResponse().getRecipients().get(0);
        final Content content = BatchResponseFactory.batchResponse().getContent().get(0);
        when(emailRequestMapper.toEmailPostRequestBody(recipient, content)).thenReturn(EmailRequestFactory.createRandomEmailRequest());
        emailNotificationService.processRecipient(recipient,
                content);
        verify(emailRequestMapper, times(1)).toEmailPostRequestBody(recipient, content);
    }

    @Test
    public void processRecipient_withEncoded_body(){
        final Recipient recipient = BatchResponseFactory.batchResponse().getRecipients().get(0);
        final Content content = BatchResponseFactory.batchResponse().getContent().get(0);

        final EmailPostRequestBody emailRequest = EmailRequestFactory.createRandomEmailRequest();
        emailRequest.setBody("This is your otp: 123456");
        emailRequest.setFrom(null);
        when(emailRequestMapper.toEmailPostRequestBody(recipient, content)).thenReturn(emailRequest);
        emailNotificationService.processRecipient(recipient,
                content);
        verify(emailRequestMapper, times(1)).toEmailPostRequestBody(recipient, content);
    }
}
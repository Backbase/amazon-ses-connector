package com.backbase.productled.service;

import com.backbase.email.integration.rest.spec.v2.email.EmailPostRequestBody;
import com.backbase.outbound.integration.communications.rest.spec.v1.model.Content;
import com.backbase.outbound.integration.communications.rest.spec.v1.model.Recipient;
import com.backbase.productled.config.DefaultMailSenderProperties;
import com.backbase.productled.mapper.EmailV1Mapper;
import com.backbase.productled.testutils.EmailPostRequestBodyFactory;
import com.backbase.productled.testutils.EmailV1Factory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.test.context.junit4.SpringRunner;

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
    EmailV1Mapper emailV1Mapper;

    @Test
    public void processRecipient() {
        final Recipient recipient = EmailV1Factory.emailV1().getRecipients().get(0);
        final Content content = EmailV1Factory.emailV1().getContent().get(0);
        when(emailV1Mapper.toEmailPostRequestBody(recipient, content)).thenReturn(EmailPostRequestBodyFactory.createRandomEmail());
        emailNotificationService.sendEmailV1(recipient,
                content);
        verify(emailV1Mapper, times(1)).toEmailPostRequestBody(recipient, content);
    }

    @Test
    public void processRecipient_withEncoded_body() {
        final Recipient recipient = EmailV1Factory.emailV1().getRecipients().get(0);
        final Content content = EmailV1Factory.emailV1().getContent().get(0);

        final EmailPostRequestBody emailPostRequestBody = EmailPostRequestBodyFactory.createRandomEmail();
        emailPostRequestBody.setBody("This is your otp: 123456");
        emailPostRequestBody.setFrom(null);
        when(emailV1Mapper.toEmailPostRequestBody(recipient, content)).thenReturn(emailPostRequestBody);
        emailNotificationService.sendEmailV1(recipient,
                content);
        verify(emailV1Mapper, times(1)).toEmailPostRequestBody(recipient, content);
    }
}
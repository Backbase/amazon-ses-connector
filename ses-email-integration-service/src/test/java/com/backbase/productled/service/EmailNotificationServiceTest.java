package com.backbase.productled.service;

import com.backbase.outbound.integration.communications.rest.spec.v1.model.Content;
import com.backbase.outbound.integration.communications.rest.spec.v1.model.Recipient;
import com.backbase.productled.config.DefaultMailSenderProperties;
import com.backbase.productled.mapper.EmailV1Mapper;
import com.backbase.productled.model.Email;
import com.backbase.productled.testutils.EmailFactory;
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
    EmailServiceV1 emailServiceV1;
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
        when(emailV1Mapper.toEmail(recipient, content)).thenReturn(EmailFactory.createRandomEmail());
        emailServiceV1.sendEmailV1(recipient, content);
        verify(emailV1Mapper, times(1)).toEmail(recipient, content);
    }

    @Test
    public void processRecipient_withEncoded_body() {
        final Recipient recipient = EmailV1Factory.emailV1().getRecipients().get(0);
        final Content content = EmailV1Factory.emailV1().getContent().get(0);

        final Email email = EmailFactory.createRandomEmail();
        email.setBody("This is your otp: 123456");
        email.setFrom(null);
        when(emailV1Mapper.toEmail(recipient, content)).thenReturn(email);
        emailServiceV1.sendEmailV1(recipient, content);
        verify(emailV1Mapper, times(1)).toEmail(recipient, content);
    }
}
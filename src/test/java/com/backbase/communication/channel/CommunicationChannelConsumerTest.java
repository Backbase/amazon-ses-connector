package com.backbase.communication.channel;

import com.backbase.communication.service.EmailNotificationService;
import com.backbase.communication.testutils.EmailV1Factory;
import com.backbase.communication.testutils.EmailV2Factory;
import com.backbase.communication.validator.EmailV1Validator;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@SpringBootTest
@RunWith(SpringRunner.class)
@ActiveProfiles("it")
public class CommunicationChannelConsumerTest {

    @Autowired
    CommunicationChannelConsumer communicationChannelConsumer;

    @Autowired
    EmailV1Validator emailV1Validator;

    @MockBean
    EmailNotificationService emailNotificationService;

    @Test
    public void acceptEmailV1Test() {
        communicationChannelConsumer.accept(EmailV1Factory.emailV1());
        verify(emailNotificationService, times(1)).sendEmail(any());
    }

    @Test
    public void acceptEmailV2Test() {
        communicationChannelConsumer.accept(EmailV2Factory.createRandomEmailV2());
        verify(emailNotificationService, times(1)).sendEmail(any());
    }
}

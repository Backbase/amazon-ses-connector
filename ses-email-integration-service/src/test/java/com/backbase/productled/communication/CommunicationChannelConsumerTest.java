package com.backbase.productled.communication;

import com.backbase.productled.service.EmailNotificationService;
import com.backbase.productled.testutils.BatchResponseFactory;
import com.backbase.productled.validator.BatchResponseValidator;
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
    BatchResponseValidator batchResponseValidator;

    @MockBean
    EmailNotificationService emailNotificationService;

    @Test
    public void accept() {
        communicationChannelConsumer.accept(BatchResponseFactory.batchResponse());
        verify(emailNotificationService, times(1)).processRecipient(any(), any());
    }
}

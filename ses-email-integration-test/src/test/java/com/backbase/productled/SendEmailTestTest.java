package com.backbase.productled;

import com.backbase.productled.reader.EmailReader;
import com.backbase.productled.sender.MessageSender;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@SpringBootTest
@RunWith(SpringRunner.class)
@Slf4j
class SendEmailTestTest {
    @Autowired
    private SendEmailTest sendEmailTest;

    @MockBean
    private MessageSender messageSender;

    @MockBean
    private EmailReader emailReader;

    @Test
    void sendEmailV1Test() throws Exception {
        try {
            sendEmailTest.sendEmailV1Test();
        } catch (Exception e) {
            log.debug("", e);
        }
        verify(messageSender, times(1)).sendMessage(any());
        verify(emailReader, times(1)).getEmails(any(), any());
    }

    @Test
    void sendEmailV2Test() throws Exception {
        try {
            sendEmailTest.sendEmailV2Test();
        } catch (Exception e) {
            log.debug("", e);
        }
        verify(messageSender, times(1)).sendMessage(any());
        verify(emailReader, times(1)).getEmails(any(), any());
    }
}
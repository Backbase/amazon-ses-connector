package com.backbase.productled;

import com.backbase.outbound.integration.communications.rest.spec.v1.model.BatchResponse;
import com.backbase.productled.reader.EmailReader;
import com.backbase.productled.sender.MessageSender;
import com.backbase.productled.util.TestMessageBuilder;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.input.NullInputStream;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import javax.mail.Address;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.ByteArrayInputStream;
import java.util.List;

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
        Mockito.when(emailReader.getEmails(any(), any())).then(invocationOnMock -> getMessageList());
        Mockito.when(emailReader.getTextFromMessage(any())).thenReturn(getMessageBody());
        try {
            sendEmailTest.sendEmailV1Test();
        } catch (Exception e) {
            log.debug("", e);
        }
        verify(messageSender, times(1)).sendMessage(any());
        verify(emailReader, times(1)).getEmails(any(), any());
    }

    private String getMessageBody() {
        TestMessageBuilder testMessageBuilder = new TestMessageBuilder();
        return testMessageBuilder.createMessageV1().getPayload().getContent().get(0).getBody();
    }

    private List<Message> getMessageList() throws MessagingException {
        TestMessageBuilder testMessageBuilder = new TestMessageBuilder();
        com.backbase.productled.model.Message<BatchResponse> messageV1 = testMessageBuilder.createMessageV1();
        Message message = new MimeMessage(null, new ByteArrayInputStream(new byte[0])) {
            @Override
            public Address[] getFrom() throws MessagingException {
                return new Address[]{new InternetAddress(messageV1.getPayload().getRecipients().get(0).getFrom())};
            }

            @Override
            public Address[] getRecipients(Message.RecipientType type) throws MessagingException {
                return new Address[]{new InternetAddress(messageV1.getPayload().getRecipients().get(0).getTo().get(0))};
            }

            @Override
            public String getSubject() {
                return messageV1.getPayload().getContent().get(0).getTitle();
            }
        };
        return List.of(message);
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
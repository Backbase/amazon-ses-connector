package com.backbase.productled;

import com.backbase.communication.event.spec.v1.EmailChannelEvent;
import com.backbase.email.integration.rest.spec.v2.email.EmailPostRequestBody;
import com.backbase.productled.model.Message;
import com.backbase.productled.reader.EmailReader;
import com.backbase.productled.sender.MessageSender;
import com.backbase.productled.util.TestMessageBuilder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.validation.ValidationException;
import java.text.MessageFormat;
import java.util.List;

@Component
@Slf4j
@RequiredArgsConstructor
public class SendEmailTest {
    private final MessageSender messageSender;

    private final EmailReader emailReader;

    public void sendEmailV1Test() throws Exception {
        TestMessageBuilder testMessageBuilder = new TestMessageBuilder();
        Message<com.backbase.outbound.integration.communications.rest.spec.v1.model.BatchResponse> message = testMessageBuilder.createMessageV1();
        messageSender.sendMessage(message);

        Thread.sleep(5000);

        String toAddress = message.getPayload().getRecipients().get(0).getTo().get(0);
        retrieveAndCheckEmails(message, toAddress);
    }

    private void checkEmailCount(List<javax.mail.Message> emails) throws ValidationException {
        if (emails.size() != 1)
            throw new ValidationException(MessageFormat.format("Invalid number of emails. expect 1 but is {0}.", emails.size()));
    }

    private void checkReceivedEmail(javax.mail.Message email, Message<?> message) throws Exception {
        checkEmailFrom(email, message);
        checkToAddress(email, message);
        checkSubject(email, message);
        checkEmailBody(email, message);
    }

    private void checkEmailBody(javax.mail.Message email, Message<?> message) throws Exception {
        String expectedBody = "";
        if (message.getPayload() instanceof com.backbase.outbound.integration.communications.rest.spec.v1.model.BatchResponse batchResponse)
            expectedBody = batchResponse.getContent().get(0).getBody();
        else if (message.getPayload() instanceof EmailPostRequestBody emailPostRequestBody) {
            expectedBody = emailPostRequestBody.getBody();
        }
        String realBody = emailReader.getTextFromMessage(email);
        if (!realBody.equals(expectedBody))
            throw new ValidationException(MessageFormat.format("Invalid email body. Expect \"{0}\" but is \"{1}\"", expectedBody, realBody));
    }

    private void checkSubject(javax.mail.Message email, Message<?> message) throws Exception {
        String expectedSubject = "";
        if (message.getPayload() instanceof com.backbase.outbound.integration.communications.rest.spec.v1.model.BatchResponse batchResponse)
            expectedSubject = batchResponse.getContent().get(0).getTitle();
        else if (message.getPayload() instanceof EmailPostRequestBody emailPostRequestBody) {
            expectedSubject = emailPostRequestBody.getSubject();
        }
        if (!email.getSubject().equals(expectedSubject))
            throw new ValidationException(MessageFormat.format("Invalid email subject. Expect {0} but is {1}", expectedSubject, email.getSubject()));
    }

    private void checkToAddress(javax.mail.Message email, Message<?> message) throws Exception {
        String expectedTo = "";
        if (message.getPayload() instanceof com.backbase.outbound.integration.communications.rest.spec.v1.model.BatchResponse batchResponse)
            expectedTo = batchResponse.getRecipients().get(0).getTo().get(0);
        else if (message.getPayload() instanceof EmailPostRequestBody emailPostRequestBody) {
            expectedTo = emailPostRequestBody.getTo().get(0);
        }
        String realTo = email.getRecipients(javax.mail.Message.RecipientType.TO)[0].toString();
        if (!realTo.equals(expectedTo))
            throw new ValidationException(MessageFormat.format("Invalid to address. Expect {0} but is {1}", expectedTo, realTo));
    }

    private void checkEmailFrom(javax.mail.Message email, Message<?> message) throws Exception {
        String expectedFrom = "";
        if (message.getPayload() instanceof com.backbase.outbound.integration.communications.rest.spec.v1.model.BatchResponse batchResponse)
            expectedFrom = batchResponse.getRecipients().get(0).getFrom();
        else if (message.getPayload() instanceof EmailPostRequestBody emailPostRequestBody) {
            expectedFrom = emailPostRequestBody.getFrom();
        }

        String receivedFrom = email.getFrom()[0].toString().replace("\"", "");
        if (!receivedFrom.equals(expectedFrom))
            throw new ValidationException(MessageFormat.format("Invalid from address. Expect {0} but is {1}", expectedFrom, receivedFrom));
    }


    public void sendEmailV2Test() throws Exception {
        TestMessageBuilder testMessageBuilder = new TestMessageBuilder();
        Message<EmailChannelEvent> message = testMessageBuilder.createMessageV2();
        messageSender.sendMessage(message);

        Thread.sleep(5000);

        String toAddress = message.getPayload().getTo().get(0);
        retrieveAndCheckEmails(message, toAddress);
    }

    private void retrieveAndCheckEmails(Message<?> message, String toAddress) throws Exception {
        List<javax.mail.Message> emails = emailReader.getEmails(toAddress, toAddress);
        log.debug("received emails: {0}", emails);
        checkEmailCount(emails);
        for (javax.mail.Message email : emails) {
            log.info("email = " + email.toString());
            checkReceivedEmail(email, message);
        }
        emailReader.close();
    }
}

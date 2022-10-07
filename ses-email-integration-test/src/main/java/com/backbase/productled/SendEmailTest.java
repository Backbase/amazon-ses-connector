package com.backbase.productled;

import com.backbase.productled.model.Message;
import com.backbase.productled.reader.EmailReader;
import com.backbase.productled.sender.MessageSender;
import com.backbase.productled.util.TestMessageBuilder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.text.MessageFormat;
import java.util.List;

@Component
@Slf4j
@RequiredArgsConstructor
public class SendEmailTest {
    private final MessageSender messageSender;

    private final EmailReader emailReader;

    public void sendEmailTest() throws Exception {
        TestMessageBuilder testMessageBuilder = new TestMessageBuilder();
        Message message = testMessageBuilder.createMessage();
        messageSender.sendMessage(message);

        Thread.sleep(5000);

        String toAddress = message.getPayload().getRecipients().get(0).getTo().get(0);
        List<javax.mail.Message> emails = emailReader.getEmails(toAddress, toAddress);
        checkEmailCount(emails);
        for (javax.mail.Message email : emails) {
            log.info("email = " + email.toString());
            checkReceivedEmail(email, message);
        }
        emailReader.close();
    }

    private void checkEmailCount(List<javax.mail.Message> emails) throws Exception {
        if (emails.size() != 1)
            throw new Exception(MessageFormat.format("Invalid number of emails. expect 1 but is {0}.", emails.size()));
    }

    private void checkReceivedEmail(javax.mail.Message email, Message message) throws Exception {
        checkEmailFrom(email, message);
        checkToAddress(email, message);
        checkSubject(email, message);
        checkEmailBody(email, message);
    }

    private void checkEmailBody(javax.mail.Message email, Message message) throws Exception {
        String expectedBody = message.getPayload().getContent().get(0).getBody();
        String realBody = emailReader.getTextFromMessage(email);
        if (!realBody.equals(expectedBody))
            throw new Exception(MessageFormat.format("Invalid email body. Expect \"{0}\" but is \"{1}\"", expectedBody, realBody));
    }

    private void checkSubject(javax.mail.Message email, Message message) throws Exception {
        String expectedSubject = message.getPayload().getContent().get(0).getTitle();
        if (!email.getSubject().equals(expectedSubject))
            throw new Exception(MessageFormat.format("Invalid email subject. Expect {0} but is {1}", expectedSubject, email.getSubject()));

    }

    private void checkToAddress(javax.mail.Message email, Message message) throws Exception {
        String realTo = email.getRecipients(javax.mail.Message.RecipientType.TO)[0].toString();
        String expectedTo = message.getPayload().getRecipients().get(0).getTo().get(0);
        if (!realTo.equals(expectedTo))
            throw new Exception(MessageFormat.format("Invalid to address. Expect {0} but is {1}", expectedTo, realTo));
    }

    private void checkEmailFrom(javax.mail.Message email, Message message) throws Exception {
        String expectedFrom = message.getPayload().getRecipients().get(0).getFrom();
        String receivedFrom = email.getFrom()[0].toString().replace("\"", "");
        if (!receivedFrom.equals(expectedFrom))
            throw new Exception(MessageFormat.format("Invalid from address. Expect {0} but is {1}", expectedFrom, receivedFrom));
    }


}

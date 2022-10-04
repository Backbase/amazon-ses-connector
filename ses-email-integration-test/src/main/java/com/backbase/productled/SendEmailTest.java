package com.backbase.productled;

import com.backbase.productled.model.Message;
import com.backbase.productled.reader.EmailReader;
import com.backbase.productled.sender.MessageSender;
import com.backbase.productled.util.TestMessageBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import java.io.IOException;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@Component
@RequiredArgsConstructor
public class SendEmailTest {
    private final MessageSender messageSender;

    private final EmailReader emailReader;

    public void sendEmailTest() throws MessagingException, InterruptedException, IOException {
        TestMessageBuilder testMessageBuilder = new TestMessageBuilder();
        Message message = testMessageBuilder.createMessage();
        messageSender.sendMessage(message);

        Thread.sleep(5000);

        String toAddress = message.getPayload().getRecipients().get(0).getTo().get(0);
        List<javax.mail.Message> emails = emailReader.getEmails(toAddress, toAddress);
        assertThat(emails).hasSize(1);
        for (javax.mail.Message email : emails) {
            System.out.println("email = " + email);
            assertThat(email.getFrom()).extracting(address -> address.toString().replace("\"","")).containsExactly(message.getPayload().getRecipients().get(0).getFrom());
            assertThat(email.getRecipients(javax.mail.Message.RecipientType.TO)[0].toString()).isNotEqualTo(message.getPayload().getRecipients().get(0).getTo().get(0));
            assertThat(email.getSubject()).isEqualTo(message.getPayload().getContent().get(0).getTitle());
            assertThat(emailReader.getTextFromMessage(email)).isEqualTo(message.getPayload().getContent().get(0).getBody());
        }
        emailReader.close();
    }
}

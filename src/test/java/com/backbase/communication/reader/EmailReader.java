package com.backbase.communication.reader;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.mail.*;
import javax.mail.internet.ContentType;
import javax.mail.internet.MimeMultipart;
import java.io.IOException;
import java.util.List;
import java.util.Properties;

@Component
@Slf4j
public class EmailReader {
    @Value("${mail.pop3.host}")
    private String host;

    @Value("${mail.pop3.port}")
    private String port;

    @Value("${mail.store.protocol}")
    private String protocol;

    private Store store;
    Folder inbox;

    public List<Message> getEmails(String username, String password) throws MessagingException {
        // 1. Setup properties for the mail session.
        Properties props = new Properties();
        props.put("mail.pop3.port", port);
        props.put("mail.pop3.host", host);
        props.put("mail.pop3.user", username);
        props.put("mail.store.protocol", protocol);

        // 2. Creates a javax.mail.Authenticator object.
        Authenticator auth = new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        };

        Session session = Session.getDefaultInstance(props, auth);
        log.debug("session = " + session);

        store = session.getStore(protocol);
        log.debug("store = " + store);
        store.connect(host, username, password);

        inbox = store.getFolder("INBOX");
        inbox.open(Folder.READ_ONLY);

        Message[] messages = inbox.getMessages();

        return List.of(messages);
    }

    public void close() throws MessagingException {
        inbox.close(false);
        store.close();
    }

    public String getTextFromMessage(Message message) throws IOException, MessagingException {
        String result = "";
        if (message.isMimeType("text/plain")) {
            result = message.getContent().toString();
        } else if (message.isMimeType("multipart/*")) {
            MimeMultipart mimeMultipart = (MimeMultipart) message.getContent();
            result = getTextFromMimeMultipart(mimeMultipart);
        }
        return result;
    }

    private String getTextFromMimeMultipart(
            MimeMultipart mimeMultipart) throws IOException, MessagingException {

        int count = mimeMultipart.getCount();
        if (count == 0)
            throw new MessagingException("Multipart with no body parts not supported.");
        boolean multipartAlt = new ContentType(mimeMultipart.getContentType()).match("multipart/alternative");
        if (multipartAlt)
            return getTextFromBodyPart(mimeMultipart.getBodyPart(count - 1));
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < count; i++) {
            BodyPart bodyPart = mimeMultipart.getBodyPart(i);
            result.append(getTextFromBodyPart(bodyPart));
        }
        return result.toString();
    }

    private String getTextFromBodyPart(
            BodyPart bodyPart) throws IOException, MessagingException {

        String result = "";
        if (bodyPart.isMimeType("text/plain")) {
            result = (String) bodyPart.getContent();
        } else if (bodyPart.isMimeType("text/html")) {
            result = (String) bodyPart.getContent();
        } else if (bodyPart.getContent() instanceof MimeMultipart mimeMultipart){
            result = getTextFromMimeMultipart(mimeMultipart);
        }
        return result;
    }
}
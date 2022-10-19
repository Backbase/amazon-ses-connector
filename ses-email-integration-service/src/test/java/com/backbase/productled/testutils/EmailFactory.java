package com.backbase.productled.testutils;

import com.backbase.productled.model.Attachment;
import com.backbase.productled.model.Email;

import java.util.*;

import static java.util.Arrays.asList;

public final class EmailFactory {

    private EmailFactory() {

    }

    public static Email createRandomEmail() {
        final Email email = new Email();
        email.setFrom("from.address@email.com");
        email.setSubject("Email subject");
        email.setBody("body");
        email.setTo(
                asList(
                        "recipient1@email.com",
                        "recipient2@email.com"
                )
        );
        email.setCc(
                asList(
                        "cc_recipient1@email.com",
                        "cc_recipient2@email.com"
                )
        );

        email.setBcc(
                asList(
                        "bcc_recipient1@email.com",
                        "bcc_recipient2@email.com"
                )
        );
        email.setImportant(new Random().nextBoolean());
        email.setAttachments(generateAttachments());

        return email;
    }

    private static List<Attachment> generateAttachments() {
        final ArrayList<Attachment> attachments = new ArrayList<>();
        attachments.add(
                Attachment.builder()
                        .fileName("cats1.png")
                        .content(new String(Base64.getEncoder().encode(createRandomString().getBytes()))).build()
        );
        attachments.add(
                Attachment.builder()
                        .fileName("dogs1.png")
                        .content(new String(Base64.getEncoder().encode(createRandomString().getBytes()))).build()
        );
        return attachments;
    }

    private static String createRandomString() {
        return UUID.randomUUID().toString();
    }

}

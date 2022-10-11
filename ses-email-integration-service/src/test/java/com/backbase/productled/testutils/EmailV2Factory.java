package com.backbase.productled.testutils;

import com.backbase.email.integration.rest.spec.v2.email.Attachment;
import com.backbase.productled.model.EmailV2;

import java.util.*;

import static java.util.Arrays.asList;

public final class EmailV2Factory {

    private EmailV2Factory() {

    }

    public static EmailV2 createRandomEmailV2() {
        final EmailV2 emailV2 = new EmailV2();
        emailV2.setFrom("from.address@email.com");
        emailV2.setSubject("Email subject");
        emailV2.setBody("body");
        emailV2.setTo(
            asList(
                "recipient1@email.com",
                "recipient2@email.com"
            )
        );
        emailV2.setCc(
            asList(
                "cc_recipient1@email.com",
                "cc_recipient2@email.com"
            )
        );

        emailV2.setBcc(
            asList(
                "bcc_recipient1@email.com",
                "bcc_recipient2@email.com"
            )
        );
        emailV2.setImportant(new Random().nextBoolean());
        emailV2.setAttachments(generateAttachments());

        return emailV2;
    }

    private static List<Attachment> generateAttachments() {
        final ArrayList<Attachment> attachments = new ArrayList<>();
        attachments.add(
            new Attachment()
                .fileName("cats1.png")
                .content(new String(Base64.getEncoder().encode(createRandomString().getBytes())))
        );
        attachments.add(
            new Attachment()
                .fileName("dogs1.png")
                .content(new String(Base64.getEncoder().encode(createRandomString().getBytes())))
        );
        return attachments;
    }

    private static String createRandomString() {
        return UUID.randomUUID().toString();
    }

}

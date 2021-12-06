package com.backbase.productled.testutils;

import com.backbase.email.integration.rest.spec.v2.email.Attachment;
import com.backbase.email.integration.rest.spec.v2.email.EmailPostRequestBody;
import java.util.*;

import static java.util.Arrays.asList;

public final class EmailRequestFactory {

    private EmailRequestFactory() {

    }

    public static EmailPostRequestBody createRandomEmailRequest() {
        final EmailPostRequestBody emailPostRequestBody = new EmailPostRequestBody();
        emailPostRequestBody.setFrom("from.address@email.com");
        emailPostRequestBody.setSubject("Email subject");
        emailPostRequestBody.setBody("body");
        emailPostRequestBody.setTo(
            asList(
                "recipient1@email.com",
                "recipient2@email.com"
            )
        );
        emailPostRequestBody.setCc(
            asList(
                "cc_recipient1@email.com",
                "cc_recipient2@email.com"
            )
        );

        emailPostRequestBody.setBcc(
            asList(
                "bcc_recipient1@email.com",
                "bcc_recipient2@email.com"
            )
        );
        emailPostRequestBody.setImportant(new Random().nextBoolean());
        emailPostRequestBody.setAttachments(generateAttachments());

        return emailPostRequestBody;
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

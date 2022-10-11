package com.backbase.productled.util;

import com.backbase.email.integration.rest.spec.v2.email.EmailPostRequestBody;
import com.backbase.outbound.integration.communications.rest.spec.v1.model.Content;
import com.backbase.productled.model.Message;

import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.UUID;

public class TestMessageBuilder {
    public Message<com.backbase.outbound.integration.communications.rest.spec.v1.model.BatchResponse> createMessageV1() {
        Message message = new Message();
        message.setPriority(1);
        message.setTrackingId(UUID.randomUUID());
        message.setTag("tag");
        message.setExpiresAt(ZonedDateTime.now().plus(1, ChronoUnit.HOURS));
        message.setCallbackUrl("callbackUrl");
        message.setDeliveryChannel("email");
        com.backbase.outbound.integration.communications.rest.spec.v1.model.BatchResponse batchResponse = new com.backbase.outbound.integration.communications.rest.spec.v1.model.BatchResponse();
        Content content = new Content();
        content.setBody("body");
        content.setContentId("content-id");
        content.setTitle("title");
        batchResponse.setContent(List.of(content));
        com.backbase.outbound.integration.communications.rest.spec.v1.model.Recipient recipient = new com.backbase.outbound.integration.communications.rest.spec.v1.model.Recipient();
        recipient.setContentId("content-id");
        recipient.setFrom("test@backbase.com");
        recipient.setRef("ref");
        recipient.setTo(List.of("sender@backbase.com"));
        batchResponse.setRecipients(List.of(recipient));
        message.setPayload(batchResponse);
        return message;
    }

    public Message<EmailPostRequestBody> createMessageV2() {
        Message<EmailPostRequestBody> message = new Message();
        message.setPriority(2);
        message.setTrackingId(UUID.randomUUID());
        message.setTag("tagV2");
        message.setExpiresAt(ZonedDateTime.now().plus(1, ChronoUnit.HOURS));
        message.setCallbackUrl("callbackUrl");
        message.setDeliveryChannel("email");
        EmailPostRequestBody emailPostRequestBody = new EmailPostRequestBody();
        emailPostRequestBody.setBody("bodyV2");
        emailPostRequestBody.setSubject("titleV2");
        emailPostRequestBody.setFrom("testV2@backbase.com");
        emailPostRequestBody.setTo(List.of("senderV2@backbase.com"));
        message.setPayload(emailPostRequestBody);
        return message;
    }
}

package com.backbase.productled.util;

import com.backbase.outbound.integration.communications.rest.spec.v1.model.Content;
import com.backbase.productled.model.Message;

import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.UUID;

public class TestMessageBuilder {
    public Message createMessage() {
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
}

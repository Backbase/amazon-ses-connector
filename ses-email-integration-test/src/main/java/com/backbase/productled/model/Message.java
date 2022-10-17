package com.backbase.productled.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.ZonedDateTime;
import java.util.UUID;

@Getter
@Setter
@ToString
public class Message {

    private UUID trackingId;
    private String deliveryChannel;
    private Integer priority;
    private String tag;
    private com.backbase.outbound.integration.communications.rest.spec.v1.model.BatchResponse payload;
    private ZonedDateTime expiresAt;
    private String callbackUrl;
}

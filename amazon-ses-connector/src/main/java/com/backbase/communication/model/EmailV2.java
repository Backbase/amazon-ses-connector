package com.backbase.communication.model;


import com.backbase.communication.event.spec.v1.EmailChannelEvent;

public class EmailV2 extends EmailChannelEvent implements Sendable {
    @Override
    public EmailVersionEnum getVersion() {
        return EmailVersionEnum.V2;
    }
}

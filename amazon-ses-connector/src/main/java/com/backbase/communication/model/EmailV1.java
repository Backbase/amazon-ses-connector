package com.backbase.communication.model;

import com.backbase.outbound.integration.communications.rest.spec.v1.model.BatchResponse;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class EmailV1 extends BatchResponse implements Sendable {

    @Override
    public EmailVersionEnum getVersion() {
        return EmailVersionEnum.V1;
    }

    public EmailV1(BatchResponse batchResponse) {
        setRecipients(batchResponse.getRecipients());
        setContent(batchResponse.getContent());
    }
}

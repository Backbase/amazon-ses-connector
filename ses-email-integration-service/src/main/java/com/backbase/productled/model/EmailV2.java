package com.backbase.productled.model;


import com.backbase.email.integration.rest.spec.v2.email.EmailPostRequestBody;

public class EmailV2 extends EmailPostRequestBody implements Sendable {
    @Override
    public EmailVersionEnum getVersion() {
        return EmailVersionEnum.V2;
    }
}

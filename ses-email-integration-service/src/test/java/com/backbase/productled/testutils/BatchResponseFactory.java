package com.backbase.productled.testutils;

import com.backbase.outbound.integration.communications.rest.spec.v1.model.BatchResponse;
import com.backbase.outbound.integration.communications.rest.spec.v1.model.Content;
import com.backbase.outbound.integration.communications.rest.spec.v1.model.Recipient;

import java.util.Collections;
import java.util.List;
import java.util.Map;

public class BatchResponseFactory {

    public static BatchResponse batchResponse() {
        return new BatchResponse()
                .recipients(List.of(new Recipient()
                        .ref("1")
                        .from("+123456789")
                        .to(List.of("+01456789"))
                        .contentId("1")
                        .data(Map.of("otp", "123456"))))
                .content(List.of(new Content()
                        .contentId("1")
                        .title("OTP")
                        .body("This is your {otp}")));
    }

    public static BatchResponse emptyRecipientBatchResponse() {
        return new BatchResponse()
                .recipients(Collections.emptyList())
                .content(List.of(new Content()
                        .contentId("1")
                        .title("OTP")
                        .body("This is your {otp}")));
    }

    public static BatchResponse emptyContentBatchResponse() {
        return new BatchResponse()
                .recipients(List.of(new Recipient()
                        .ref("1")
                        .from("+123456789")
                        .to(List.of("+01456789"))
                        .contentId("1")
                        .data(Map.of("otp", "123456"))))
                .content(Collections.emptyList());
    }

    public static BatchResponse mismatchedContentIdBatchResponse() {
        return new BatchResponse()
                .recipients(List.of(new Recipient()
                        .ref("1")
                        .from("+123456789")
                        .to(List.of("+01456789"))
                        .contentId("1")
                        .data(Map.of("otp", "123456"))))
                .content(List.of(new Content()
                        .contentId("2")
                        .title("OTP")
                        .body("This is your {otp}")));
    }

}

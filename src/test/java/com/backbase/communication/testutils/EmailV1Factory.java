package com.backbase.communication.testutils;

import com.backbase.outbound.integration.communications.rest.spec.v1.model.BatchResponse;
import com.backbase.outbound.integration.communications.rest.spec.v1.model.Content;
import com.backbase.outbound.integration.communications.rest.spec.v1.model.Recipient;
import com.backbase.communication.model.EmailV1;

import java.util.Collections;
import java.util.List;
import java.util.Map;

public class EmailV1Factory {

    public static EmailV1 emailV1() {
        return new EmailV1(new BatchResponse()
                .recipients(List.of(new Recipient()
                        .ref("1")
                        .from("+123456789")
                        .to(List.of("+01456789"))
                        .contentId("1")
                        .data(Map.of("otp", "123456"))))
                .content(List.of(new Content()
                        .contentId("1")
                        .title("OTP")
                        .body("This is your {otp}"))));
    }

    public static EmailV1 emptyRecipientEmailV1() {
        return new EmailV1(new BatchResponse()
                .recipients(Collections.emptyList())
                .content(List.of(new Content()
                        .contentId("1")
                        .title("OTP")
                        .body("This is your {otp}"))));
    }

    public static EmailV1 emptyContentEmailV1() {
        return new EmailV1(new BatchResponse()
                .recipients(List.of(new Recipient()
                        .ref("1")
                        .from("+123456789")
                        .to(List.of("+01456789"))
                        .contentId("1")
                        .data(Map.of("otp", "123456"))))
                .content(Collections.emptyList()));
    }

    public static EmailV1 mismatchedContentIdEmailV1() {
        return new EmailV1(new BatchResponse()
                .recipients(List.of(new Recipient()
                        .ref("1")
                        .from("+123456789")
                        .to(List.of("+01456789"))
                        .contentId("1")
                        .data(Map.of("otp", "123456"))))
                .content(List.of(new Content()
                        .contentId("2")
                        .title("OTP")
                        .body("This is your {otp}"))));
    }

}

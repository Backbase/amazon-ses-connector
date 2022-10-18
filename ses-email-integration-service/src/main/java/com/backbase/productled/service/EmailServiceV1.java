package com.backbase.productled.service;

import com.backbase.productled.model.EmailV1;
import com.backbase.productled.model.EmailVersionEnum;
import com.backbase.productled.model.Sendable;
import com.backbase.productled.validator.EmailV1Validator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class EmailServiceV1 implements EmailService {
    private final EmailV1Validator emailV1Validator;
    private final EmailNotificationService emailNotificationService;

    @Override
    public EmailVersionEnum getVersion() {
        return EmailVersionEnum.V1;
    }

    @Override
    public void handleEmail(Sendable sendable) {
        EmailV1 emailV1 = (EmailV1) sendable;
        emailV1Validator.validate(emailV1);

        Map<String, com.backbase.outbound.integration.communications.rest.spec.v1.model.Content> contentMap = emailV1.getContent()
                .stream()
                .collect(Collectors.toMap(com.backbase.outbound.integration.communications.rest.spec.v1.model.Content::getContentId, Function.identity()));
        emailV1.getRecipients().forEach(recipient -> emailNotificationService.sendEmailV1(recipient, contentMap.get(recipient.getContentId())));

    }
}

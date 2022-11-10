package com.backbase.communication.service;

import com.backbase.communication.mapper.EmailV1Mapper;
import com.backbase.communication.model.Error;
import com.backbase.communication.model.*;
import com.backbase.communication.util.DeliveryCodes;
import com.backbase.communication.validator.EmailV1Validator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
@Slf4j
public class EmailServiceV1 implements EmailService {
    private final EmailV1Validator emailV1Validator;
    private final EmailNotificationService emailNotificationService;
    private final EmailV1Mapper emailV1Mapper;

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
        emailV1.getRecipients().forEach(recipient -> sendEmailV1(recipient, contentMap.get(recipient.getContentId())));
    }

    public Status sendEmailV1(com.backbase.outbound.integration.communications.rest.spec.v1.model.Recipient recipient, com.backbase.outbound.integration.communications.rest.spec.v1.model.Content content) {
        var responseStatus = new Status();
        responseStatus.setRef(recipient.getRef());

        log.debug("Content data: '{}'", content);
        log.debug("Delivering Email from: '{}' to targets: '{}'", recipient.getFrom(), recipient.getTo());

        try {
            var deliveryStatus = emailNotificationService.sendEmail(emailV1Mapper.toEmail(recipient, content));
            responseStatus.setState(deliveryStatus.getState());
            responseStatus.setError(deliveryStatus.getError());
        } catch (Exception e) {
            log.error("Communications call failed with error: {}", e.getMessage());
            responseStatus.setError(Error.builder()
                    .code(String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()))
                    .message(e.getMessage()).build());
            responseStatus.setState(DeliveryCodes.FAILED);
        }

        return responseStatus;
    }

}


package com.backbase.communication.service;

import com.backbase.communication.mapper.EmailV2Mapper;
import com.backbase.communication.model.EmailV2;
import com.backbase.communication.model.EmailVersionEnum;
import com.backbase.communication.model.Error;
import com.backbase.communication.model.Sendable;
import com.backbase.communication.model.Status;
import com.backbase.communication.util.DeliveryCodes;
import com.backbase.communication.validator.EmailV2Validator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class EmailServiceV2 implements EmailService {
    private final EmailNotificationService emailNotificationService;
    private final EmailV2Validator emailV2Validator;
    private final EmailV2Mapper emailV2Mapper;

    @Override
    public EmailVersionEnum getVersion() {
        return EmailVersionEnum.V2;
    }

    @Override
    public void handleEmail(Sendable sendable) {
        EmailV2 emailV2 = (EmailV2) sendable;
        emailV2Validator.validate(emailV2);
        sendEmailV2(emailV2);
    }

    public Status sendEmailV2(EmailV2 emailV2) {
        var responseStatus = new Status();
        Status deliveryStatus = null;

        log.debug("Content data: '{}'", emailV2.getBody());
        log.debug("Delivering Email from: '{}' to targets: '{}'", emailV2.getFrom(), emailV2.getTo());

        try {
            deliveryStatus = emailNotificationService.sendEmail(emailV2Mapper.toEmail(emailV2));
            responseStatus.setState(deliveryStatus.getState());
            responseStatus.setError(deliveryStatus.getError());
        } catch (Exception e) {
            log.error("Communications call failed with error: {}", e.getMessage());
            responseStatus.setError(Error.builder()
                    .code(String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()))
                    .message(e.getMessage()).build());
            responseStatus.setState(DeliveryCodes.FAILED);
        }

        return deliveryStatus;
    }
}

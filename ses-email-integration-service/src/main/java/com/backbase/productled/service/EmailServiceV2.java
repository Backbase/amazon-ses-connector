package com.backbase.productled.service;

import com.backbase.productled.model.EmailV2;
import com.backbase.productled.model.EmailVersionEnum;
import com.backbase.productled.model.Sendable;
import com.backbase.productled.validator.EmailV2Validator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class EmailServiceV2 implements EmailService {
    private final EmailNotificationService emailNotificationService;
    private final EmailV2Validator emailV2Validator;

    @Override
    public EmailVersionEnum getVersion() {
        return EmailVersionEnum.V2;
    }

    @Override
    public void handleEmail(Sendable sendable) {
        EmailV2 emailV2 = (EmailV2) sendable;
        emailV2Validator.validate(emailV2);
        emailNotificationService.sendEmailV2(emailV2);
    }
}

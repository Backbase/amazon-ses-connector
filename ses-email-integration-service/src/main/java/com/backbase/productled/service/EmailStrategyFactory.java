package com.backbase.productled.service;

import com.backbase.productled.model.EmailVersionEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Component
public class EmailStrategyFactory {
    private final Map<EmailVersionEnum, EmailService> emailStrategyMap;

    @Autowired
    public EmailStrategyFactory(Set<EmailService> emailStrategies) {
        emailStrategyMap = new HashMap<>();
        emailStrategies.forEach(emailService -> emailStrategyMap.put(emailService.getVersion(), emailService));
    }

    public EmailService findEmailStrategy(EmailVersionEnum emailVersionEnum) {
        if (!emailStrategyMap.containsKey(emailVersionEnum))
            throw new IllegalArgumentException(MessageFormat.format("Invalid email version: {0}", emailVersionEnum.getValue()));
        return emailStrategyMap.get(emailVersionEnum);
    }
}

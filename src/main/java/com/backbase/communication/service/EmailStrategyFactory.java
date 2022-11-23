package com.backbase.communication.service;

import com.backbase.communication.model.EmailVersionEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.MessageFormat;
import java.util.EnumMap;
import java.util.Map;
import java.util.Set;

@Component
public class EmailStrategyFactory {
    private final Map<EmailVersionEnum, EmailService> emailStrategyMap;

    @Autowired
    public EmailStrategyFactory(Set<EmailService> emailStrategies) {
        emailStrategyMap = new EnumMap<>(EmailVersionEnum.class);
        emailStrategies.forEach(emailService -> emailStrategyMap.put(emailService.getVersion(), emailService));
    }

    public EmailService findEmailStrategy(EmailVersionEnum emailVersionEnum) {
        if (!emailStrategyMap.containsKey(emailVersionEnum))
            throw new IllegalArgumentException(MessageFormat.format("Invalid email version: {0}", emailVersionEnum.getValue()));
        return emailStrategyMap.get(emailVersionEnum);
    }
}

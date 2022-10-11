package com.backbase.productled.service;

import com.backbase.productled.model.EmailVersionEnum;
import com.backbase.productled.model.Sendable;

public interface EmailService {
    EmailVersionEnum getVersion();

    void handleEmail(Sendable sendable);
}

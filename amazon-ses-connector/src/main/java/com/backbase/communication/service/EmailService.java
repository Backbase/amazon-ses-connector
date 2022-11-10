package com.backbase.communication.service;

import com.backbase.communication.model.EmailVersionEnum;
import com.backbase.communication.model.Sendable;

public interface EmailService {
    EmailVersionEnum getVersion();

    void handleEmail(Sendable sendable);
}

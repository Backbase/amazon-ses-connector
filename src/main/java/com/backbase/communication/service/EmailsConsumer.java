package com.backbase.communication.service;

import javax.mail.MessagingException;

@FunctionalInterface
public interface EmailsConsumer {
    void acceptEmails(String[] strings) throws MessagingException;
}

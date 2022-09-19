package com.backbase.productled.util;

import org.springframework.mail.javamail.MimeMessageHelper;

import java.util.Arrays;

public enum EmailPriority {

    /**
     * Email priority for MimeMessage. These integer values are hardcoded here {@link
     * MimeMessageHelper#setPriority(int)}
     */
    NORMAL(3, false),
    HIGH(1, true);

    int javaMailPriority;
    boolean important;

    EmailPriority(int javaMailPriority, boolean important) {
        this.javaMailPriority = javaMailPriority;
        this.important = important;
    }

    /**
     * Mapping of importance to MimeMessage's priority.
     */
    public static int getPriority(boolean importance) {
        return Arrays.stream(values()).filter(
            emailPriority -> emailPriority.important == importance)
            .findFirst()
            .orElse(EmailPriority.NORMAL)
            .javaMailPriority;
    }
}

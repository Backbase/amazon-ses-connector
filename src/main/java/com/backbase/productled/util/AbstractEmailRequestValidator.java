package com.backbase.productled.util;

import com.backbase.email.integration.rest.spec.v2.email.EmailPostRequestBody;
import org.springframework.validation.Validator;

public abstract class AbstractEmailRequestValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return EmailPostRequestBody.class.isAssignableFrom(clazz);
    }
}

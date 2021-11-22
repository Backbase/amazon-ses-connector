package com.backbase.productled.validator;

import com.backbase.email.integration.rest.spec.v2.email.EmailPostRequestBody;
import com.backbase.productled.util.AbstractEmailRequestValidator;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.List;

@Component
public class EmailPostRequestValidator implements Validator {

    private final List<AbstractEmailRequestValidator> emailAddressValidators;

    public EmailPostRequestValidator(List<AbstractEmailRequestValidator> emailAddressValidators) {
        this.emailAddressValidators = emailAddressValidators;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return EmailPostRequestBody.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        var emailPostRequestBody = (EmailPostRequestBody) target;
        emailAddressValidators.forEach(validator -> validator.validate(emailPostRequestBody, errors));

    }
}

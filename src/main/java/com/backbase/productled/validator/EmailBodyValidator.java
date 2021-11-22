package com.backbase.productled.validator;

import com.backbase.email.integration.rest.spec.v2.email.EmailPostRequestBody;
import com.backbase.productled.util.AbstractEmailRequestValidator;
import com.backbase.productled.util.Base64Utils;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

@Component
public class EmailBodyValidator extends AbstractEmailRequestValidator {

    private static final String BODY_FIELD = "body";

    @Override
    public void validate(Object target, Errors errors) {
        var emailPostRequestBody = (EmailPostRequestBody) target;
        if (Base64Utils.isNotBase64(emailPostRequestBody.getBody())) {
            errors.reject(BODY_FIELD, "Body is not base64 encoded");
        }
    }

}

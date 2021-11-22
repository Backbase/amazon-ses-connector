package com.backbase.productled.validator;

import com.backbase.email.integration.rest.spec.v2.email.EmailPostRequestBody;
import com.backbase.productled.util.AbstractEmailRequestValidator;
import com.nimbusds.oauth2.sdk.util.CollectionUtils;
import org.apache.commons.validator.routines.EmailValidator;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import java.util.List;
import static java.util.Objects.nonNull;

@Component
public class EmailAddressValidator extends AbstractEmailRequestValidator {

    private static final String FROM_FIELD = "from";
    private static final String TO_ADDRESS_FIELD = "to";
    private static final String CC_ADDRESS_FIELD = "cc";
    private static final String BCC_ADDRESS_FIELD = "bcc";

    @Override
    public void validate(Object target, Errors errors) {
        var emailPostRequestBody = (EmailPostRequestBody) target;
        validateFromAddress(emailPostRequestBody.getFrom(), errors);
        validateToAddresses(emailPostRequestBody.getTo(), errors);
        validateEmailAddresses(CC_ADDRESS_FIELD, emailPostRequestBody.getCc(), errors);
        validateEmailAddresses(BCC_ADDRESS_FIELD, emailPostRequestBody.getBcc(), errors);
    }

    private void validateFromAddress(String fromAddress, Errors errors) {
        if (nonNull(fromAddress)) {
            validateEmailAddress(FROM_FIELD, fromAddress, errors);
        }
    }

    private void validateToAddresses(List<String> toAddresses, Errors errors) {
        if (CollectionUtils.isEmpty(toAddresses)) {
            errors.reject(TO_ADDRESS_FIELD, "`TO` field could not be empty");
        }
        validateEmailAddresses(TO_ADDRESS_FIELD, toAddresses, errors);
    }

    private void validateEmailAddresses(String addressType, List<String> emailAddresses, Errors errors) {
        if (CollectionUtils.isNotEmpty(emailAddresses)) {
            emailAddresses.forEach(emailAddress -> validateEmailAddress(addressType, emailAddress, errors));
        }
    }

    private void validateEmailAddress(String addressType, String emailAddress, Errors errors) {
        if (isNotValidEmail(emailAddress)) {
            errors.reject(addressType, String.format("%s is not valid email address!", emailAddress));
        }
    }

    private boolean isNotValidEmail(String emailAddress) {
        return !EmailValidator.getInstance().isValid(emailAddress);
    }
}

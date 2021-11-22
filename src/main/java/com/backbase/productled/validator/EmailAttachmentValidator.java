package com.backbase.productled.validator;

import com.backbase.email.integration.rest.spec.v2.email.Attachment;
import com.backbase.email.integration.rest.spec.v2.email.EmailPostRequestBody;
import com.backbase.productled.util.AbstractEmailRequestValidator;
import com.backbase.productled.util.Base64Utils;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import java.util.List;

@Component
public class EmailAttachmentValidator extends AbstractEmailRequestValidator {

    private static final String ATTACHMENT_FIELD = "attachment";

    @Override
    public void validate(Object target, Errors errors) {
        final var emailPostRequestBody = (EmailPostRequestBody) target;

        final List<Attachment> attachments = emailPostRequestBody.getAttachments();

        if(!attachments.isEmpty()){
            attachments
                    .forEach(attachment -> {
                        if (Base64Utils.isNotBase64(attachment.getContent())) {
                            errors.reject(ATTACHMENT_FIELD, "Attachment is not base64 encoded");
                        }
                    });
        }
    }

}

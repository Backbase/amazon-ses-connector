package com.backbase.productled.mapper;

import com.backbase.email.integration.rest.spec.v2.email.Attachment;
import com.backbase.email.integration.rest.spec.v2.email.EmailPostRequestBody;
import com.backbase.productled.model.EmailV2;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface EmailV2Mapper {
    EmailPostRequestBody toEmailPostRequestBody(EmailV2 emailV2);

    Attachment toAttachment(com.backbase.communication.event.spec.v1.Attachment attachment);
}

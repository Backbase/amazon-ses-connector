package com.backbase.productled.mapper;

import com.backbase.productled.model.Attachment;
import com.backbase.productled.model.Email;
import com.backbase.productled.model.EmailV2;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface EmailV2Mapper {
    Email toEmail(EmailV2 emailV2);

    Attachment toAttachment(com.backbase.communication.event.spec.v1.Attachment attachment);
}

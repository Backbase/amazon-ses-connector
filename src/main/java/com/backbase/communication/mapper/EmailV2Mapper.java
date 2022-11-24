package com.backbase.communication.mapper;

import com.backbase.communication.model.Attachment;
import com.backbase.communication.model.Email;
import com.backbase.communication.model.EmailV2;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface EmailV2Mapper {
    Email toEmail(EmailV2 emailV2);

    Attachment toAttachment(com.backbase.communication.event.spec.v1.Attachment attachment);
}

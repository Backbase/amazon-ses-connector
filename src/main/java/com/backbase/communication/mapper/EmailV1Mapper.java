package com.backbase.communication.mapper;

import com.backbase.outbound.integration.communications.rest.spec.v1.model.Content;
import com.backbase.outbound.integration.communications.rest.spec.v1.model.Recipient;
import com.backbase.communication.model.Email;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;

@Mapper(componentModel = "spring")
public interface EmailV1Mapper {

    @Mapping(target = "to", source = "recipient", qualifiedByName = "to")
    @Mapping(target = "body", source = "content.body")
    @Mapping(target = "from", source = "recipient.from")
    @Mapping(target = "subject", source = "content.title")
    Email toEmail(Recipient recipient, Content content);

    @Named("to")
    default List<String> cleanToAddress(Recipient recipient) {
        return recipient.getTo()
                .stream()
                //Strip channel prefix off the to address -> email:sara@test.com -> sara@test.com
                .map(to -> to.replace("email:", ""))
                .toList();
    }
}

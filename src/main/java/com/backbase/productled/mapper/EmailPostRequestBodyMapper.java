package com.backbase.productled.mapper;

import com.backbase.email.integration.rest.spec.v2.email.EmailPostRequestBody;
import com.backbase.outbound.integration.communications.rest.spec.v1.model.Content;
import com.backbase.outbound.integration.communications.rest.spec.v1.model.Recipient;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface EmailPostRequestBodyMapper {

    @Mapping(target = "to", source = "recipient", qualifiedByName = "to")
    @Mapping(target = "body", source = "content.body")
    @Mapping(target = "from", source = "recipient.from")
    @Mapping(target = "subject", constant = "content.title")
    EmailPostRequestBody toEmailPostRequestBody(Recipient recipient, Content content);

    @Named("to")
    default List<String> cleanToAddress(Recipient recipient) {
        return recipient.getTo()
                .stream()
                //Strip channel prefix off the to address -> email:sara@test.com -> sara@test.com
                .map(to -> to.replace("email:", ""))
                .collect(Collectors.toList());
    }
}

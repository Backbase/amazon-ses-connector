package com.backbase.communication.model;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import org.springframework.boot.jackson.JsonMixin;

@JsonMixin({EmailV1.class, EmailV2.class})
@JsonTypeInfo(use = JsonTypeInfo.Id.DEDUCTION)
@JsonSubTypes(
        {
                @JsonSubTypes.Type(value = EmailV1.class),
                @JsonSubTypes.Type(value = EmailV2.class)

        })
public interface Sendable {
    EmailVersionEnum getVersion();
}

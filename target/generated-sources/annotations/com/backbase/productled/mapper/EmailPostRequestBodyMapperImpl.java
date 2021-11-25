package com.backbase.productled.mapper;

import com.backbase.email.integration.rest.spec.v2.email.EmailPostRequestBody;
import com.backbase.outbound.integration.communications.rest.spec.v1.model.Content;
import com.backbase.outbound.integration.communications.rest.spec.v1.model.Recipient;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2021-11-25T13:00:39+0000",
    comments = "version: 1.4.2.Final, compiler: javac, environment: Java 11.0.13 (Azul Systems, Inc.)"
)
@Component
public class EmailPostRequestBodyMapperImpl implements EmailPostRequestBodyMapper {

    @Override
    public EmailPostRequestBody toEmailPostRequestBody(Recipient recipient, Content content) {
        if ( recipient == null && content == null ) {
            return null;
        }

        EmailPostRequestBody emailPostRequestBody = new EmailPostRequestBody();

        if ( recipient != null ) {
            emailPostRequestBody.setTo( cleanToAddress( recipient ) );
            emailPostRequestBody.setFrom( recipient.getFrom() );
        }
        if ( content != null ) {
            emailPostRequestBody.setBody( content.getBody() );
        }
        emailPostRequestBody.setSubject( "Frank Bank: OTP" );

        return emailPostRequestBody;
    }
}

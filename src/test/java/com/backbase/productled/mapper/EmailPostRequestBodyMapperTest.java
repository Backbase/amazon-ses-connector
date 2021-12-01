package com.backbase.productled.mapper;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mapstruct.factory.Mappers;
import org.mockito.junit.MockitoJUnitRunner;
import com.backbase.outbound.integration.communications.rest.spec.v1.model.Content;
import com.backbase.outbound.integration.communications.rest.spec.v1.model.Recipient;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(MockitoJUnitRunner.class)
public class EmailPostRequestBodyMapperTest {


    private final String body = "Sample email body.";
    private final String title = "OTP";
    private final List<String> emailList = List.of("email:sara1@backbase.com", "email:sara@backbase.com");
    private final List<String> formattedEmailList = List.of("sara1@backbase.com", "sara@backbase.com");
    private EmailPostRequestBodyMapper mapper = Mappers.getMapper(EmailPostRequestBodyMapper.class);

    @Test
    public void toEmailPostRequestBody(){
        final var content = new Content().body(body).title(title);
        final var recipient = new Recipient().to(emailList).from("admin@backbase.com");

        final var emailPostRequestBody = mapper.toEmailPostRequestBody(recipient, content);

        assertEquals(body, emailPostRequestBody.getBody());
        assertEquals(title, emailPostRequestBody.getSubject());
        assertArrayEquals(formattedEmailList.toArray(), emailPostRequestBody.getTo().toArray());
    }
}
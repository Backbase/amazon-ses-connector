package com.backbase.communication.mapper;

import com.backbase.outbound.integration.communications.rest.spec.v1.model.Content;
import com.backbase.outbound.integration.communications.rest.spec.v1.model.Recipient;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mapstruct.factory.Mappers;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

@RunWith(MockitoJUnitRunner.class)
public class EmailV1MapperTest {

    private final List<String> emailList = List.of("email:sara1@backbase.com", "email:sara@backbase.com");
    private final List<String> formattedEmailList = List.of("sara1@backbase.com", "sara@backbase.com");
    private final EmailV1Mapper mapper = Mappers.getMapper(EmailV1Mapper.class);

    @Test
    public void toEmailV2Test(){
        String title = "OTP";
        String body = "Sample email body.";
        final var content = new Content().body(body).title(title);
        final var recipient = new Recipient().to(emailList).from("admin@backbase.com");

        final var emailV2 = mapper.toEmail(recipient, content);

        assertEquals(body, emailV2.getBody());
        assertEquals(title, emailV2.getSubject());
        assertArrayEquals(formattedEmailList.toArray(), emailV2.getTo().toArray());
    }
}
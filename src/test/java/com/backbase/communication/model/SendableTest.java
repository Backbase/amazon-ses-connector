package com.backbase.communication.model;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
@SpringBootTest
@ActiveProfiles({"default", "it"})
class SendableTest {

    @Autowired
    private ObjectMapper objectMapper;

    static {
        System.setProperty("SIG_SECRET_KEY", "JWTSecretKeyDontUseInProduction!");
    }

    @Test
    void testEmailV1Deserialization() throws JsonProcessingException {
        String jsonString = """
            {
                "recipients": [{
                    "contentId":"contentId",
                    "ref":"ref",
                    "to":["test@backbase.test"],
                    "from": "test@test.com"
                    }],
                "content":[{
                    "contentId":"contentId",
                    "body": "body",
                    "title": "title"
                }]
            }""";
        Sendable sendable = objectMapper.readValue(jsonString, Sendable.class);
        assertThat(sendable).isInstanceOf(EmailV1.class);
    }

    @Test
    void testEmailV2Deserialization() throws JsonProcessingException {
        String jsonString = """
            {
                "subject": "Important Email Subject",
                "body": "This is the email body for john",
                "to": ["test@test.com"],
                "from": "t@test.com"
            }""";
        Sendable sendable = objectMapper.readValue(jsonString, Sendable.class);
        assertThat(sendable).isInstanceOf(EmailV2.class);
    }
}
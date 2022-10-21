package com.backbase.productled.util;

import com.backbase.communication.event.spec.v1.EmailChannelEvent;
import com.backbase.outbound.integration.communications.rest.spec.v1.model.BatchResponse;
import com.backbase.productled.model.Message;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.constraints.AssertTrue;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class TestMessageBuilderTest {
    private TestMessageBuilder testMessageBuilder = new TestMessageBuilder();

    @Test
    void createMessageV1() {
        Message<BatchResponse> messageV1 = testMessageBuilder.createMessageV1();
        assertThat(messageV1).isNotNull();
    }

    @Test
    void createMessageV2() {
        Message<EmailChannelEvent> messageV2 = testMessageBuilder.createMessageV2();
        assertThat(messageV2).isNotNull();
    }
}
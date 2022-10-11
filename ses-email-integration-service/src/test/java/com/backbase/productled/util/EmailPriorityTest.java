package com.backbase.productled.util;

import com.backbase.email.integration.rest.spec.v2.email.EmailPostRequestBody;
import com.backbase.productled.testutils.EmailV2Factory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;

@RunWith(MockitoJUnitRunner.class)
public class EmailPriorityTest {

    @Test
    public void giving_An_ImportantEmail_send_with_highestPriority() {
        final EmailPostRequestBody emailRequest = EmailV2Factory.createRandomEmailV2();
        emailRequest.setImportant(Boolean.TRUE);
        assertEquals(1, EmailPriority.getPriority(emailRequest.getImportant()));
    }

    @Test
    public void giving_An_Email_send_with_normalPriority() {
        final EmailPostRequestBody emailRequest = EmailV2Factory.createRandomEmailV2();
        emailRequest.setImportant(Boolean.FALSE);
        assertEquals(3, EmailPriority.getPriority(emailRequest.getImportant()));
    }
}
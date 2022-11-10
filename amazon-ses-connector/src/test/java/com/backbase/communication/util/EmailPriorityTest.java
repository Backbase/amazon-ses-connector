package com.backbase.communication.util;

import com.backbase.communication.model.Email;
import com.backbase.communication.testutils.EmailFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;

@RunWith(MockitoJUnitRunner.class)
public class EmailPriorityTest {

    @Test
    public void giving_An_ImportantEmail_send_with_highestPriority() {
        final Email email = EmailFactory.createRandomEmail();
        email.setImportant(Boolean.TRUE);
        assertEquals(1, EmailPriority.getPriority(email.getImportant()));
    }

    @Test
    public void giving_An_Email_send_with_normalPriority() {
        final Email email = EmailFactory.createRandomEmail();
        email.setImportant(Boolean.FALSE);
        assertEquals(3, EmailPriority.getPriority(email.getImportant()));
    }
}
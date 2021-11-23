package com.backbase.productled.util;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.*;

@RunWith(MockitoJUnitRunner.class)
public class ErrorCodesTest {

    @Test
    public void getErrorCodes_001(){
        ErrorCodes errorCodes = ErrorCodes.ERR_01;
        assertEquals("comms.error.NO_RECIPIENTS", errorCodes.getErrorCode());
        assertEquals("List of recipients must not be empty.", errorCodes.getErrorMessage());
    }

    @Test
    public void getErrorCodes_002(){
        ErrorCodes errorCodes = ErrorCodes.ERR_02;
        assertEquals("comms.error.NO_CONTENT", errorCodes.getErrorCode());
        assertEquals("List of content must not be empty.", errorCodes.getErrorMessage());
    }

    @Test
    public void getErrorCodes_003(){
        ErrorCodes errorCodes = ErrorCodes.ERR_03;
        assertEquals("comms.error.MISSING_CONTENT_REF", errorCodes.getErrorCode());
        assertEquals("Recipient contentId does not reference a valid/existing Content object.", errorCodes.getErrorMessage());
    }

}
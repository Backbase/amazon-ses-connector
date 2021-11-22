package com.backbase.productled.util;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.*;

@RunWith(MockitoJUnitRunner.class)
public class Base64UtilsTest {

    String sampleString = "This is your otp: 12345.";

    @Test
    public void verify_A_String_Is_Not_A_Base64String() {
        assertTrue(Base64Utils.isNotBase64(sampleString));
    }

    @Test
    public void verify_Base64_String() {
        var base64String = "VGhpcyBpcyB5b3VyIG90cDogMTIzNDUu";
        assertEquals(sampleString, Base64Utils.decodeBase64(base64String));

    }
}
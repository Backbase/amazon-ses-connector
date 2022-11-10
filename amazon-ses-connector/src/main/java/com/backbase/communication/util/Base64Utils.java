package com.backbase.communication.util;

import org.apache.commons.codec.binary.Base64;

public final class Base64Utils {

    private Base64Utils() {
    }

    /**
     * Checks whether input string is Base64 encoded.
     *
     * @param string – input string to check.
     */
    public static boolean isNotBase64(String string) {
        return !Base64.isBase64(string);
    }

    /**
     * Decodes encoded Base64 string to a string.
     *
     * @param base64String – input string to check.
     * @return decoded String
     */
    public static String decodeBase64(String base64String) {
        byte[] decodedBytes = Base64.decodeBase64(base64String);
        return new String(decodedBytes);
    }
}
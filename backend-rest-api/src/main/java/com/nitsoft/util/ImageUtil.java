package com.nitsoft.util;

import java.util.Base64;

public class ImageUtil {

    public static String encodeToString(byte[] fileContent) {
        return Base64.getEncoder()
                .encodeToString(fileContent);
    }

    public static byte[] decodeToByteArray(String imageString) {
        return Base64.getDecoder()
                .decode(imageString);
    }
}

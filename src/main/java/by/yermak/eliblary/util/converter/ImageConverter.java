package by.yermak.eliblary.util.converter;

import org.apache.commons.codec.binary.Base64;

import java.nio.charset.StandardCharsets;

public final class ImageConverter {

    private ImageConverter() {
    }

    public static String imageToString(byte[] imageBytes) {
        byte[] encodeBase64 = Base64.encodeBase64(imageBytes);
        return new String(encodeBase64, StandardCharsets.UTF_8);
    }
}
package com.shrralis.tools;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class SecurityTool {
    private SecurityTool() {}

    public static String md5(String data) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        byte[] hash = md5(data.getBytes("UTF-8"));
        StringBuilder stringBuilder = new StringBuilder();

        for (byte b : hash) {
            stringBuilder.append(String.format("%02x", b & 0xff));
        }
        return stringBuilder.toString();
    }

    public static byte[] md5(byte[] data) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("MD5");

        md.update(data);
        return md.digest();
    }
}

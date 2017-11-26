package com.shrralis.tools;

public final class TextUtil {
    private TextUtil() {}

    public static boolean isEmpty(String s) {
        return s == null || s.equals("");
    }
}

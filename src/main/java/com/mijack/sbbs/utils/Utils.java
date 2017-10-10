package com.mijack.sbbs.utils;

import org.springframework.security.core.Authentication;

import java.util.Base64;
import java.util.regex.Pattern;

public class Utils {
    public static final Pattern PATTERN = Pattern.compile("^[a-zA-z0-9]+@[a-zA-z0-9]+(\\.[a-zA-z0-9]+)+$");

    public static boolean isEquals(String str1, String str2) {
        return str1 != null && str1.equals(str2);
    }

    public static boolean isEmpty(CharSequence charSequence) {
        return charSequence == null || charSequence.length() == 0;
    }

    public static boolean isEmail(String email) {
        return !isEmpty(email) && PATTERN.matcher(email).matches();
    }

    public static String base64Encode(String src) {
        return new String(Base64.getEncoder().encode(src.getBytes()));
    }

    public static String base64Decoder(String src) {
        return new String(Base64.getDecoder().decode(src.getBytes()));
    }

    public static boolean isAuthenticated(Authentication authentication) {
        return authentication != null && authentication.isAuthenticated();
    }
}

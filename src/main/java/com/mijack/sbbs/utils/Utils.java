package com.mijack.sbbs.utils;

import org.springframework.security.core.Authentication;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.regex.Pattern;

public class Utils {
    public static final Pattern PATTERN = Pattern.compile("^[a-zA-z0-9]+@[a-zA-z0-9]+(\\.[a-zA-z0-9]+)+$");

    public static <T> boolean isEquals(T o1, T o2) {
        return o1 != null && o1.equals(o2);
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

    public static int length(Object... objects) {
        return objects == null ? 0 : objects.length;
    }

    /**
     * 获取该输入流的MD5值
     *
     * @param is
     * @return
     * @throws NoSuchAlgorithmException
     * @throws IOException
     */
    public static String getMD5(InputStream is) throws NoSuchAlgorithmException, IOException {
        StringBuffer md5 = new StringBuffer();
        MessageDigest md = MessageDigest.getInstance("MD5");
        byte[] dataBytes = new byte[1024];

        int nread = 0;
        while ((nread = is.read(dataBytes)) != -1) {
            md.update(dataBytes, 0, nread);
        }
        ;
        byte[] mdbytes = md.digest();

        // convert the byte to hex format
        for (int i = 0; i < mdbytes.length; i++) {
            md5.append(Integer.toString((mdbytes[i] & 0xff) + 0x100, 16).substring(1));
        }
        return md5.toString();
    }

    public static String getMD5(byte[] bytes) throws NoSuchAlgorithmException {
        StringBuffer md5 = new StringBuffer();
        MessageDigest md = MessageDigest.getInstance("MD5");
        md.update(bytes, 0, bytes.length);
        byte[] mdbytes = md.digest();
        for (int i = 0; i < mdbytes.length; i++) {
            md5.append(Integer.toString((mdbytes[i] & 0xff) + 0x100, 16).substring(1));
        }
        return md5.toString();
    }

    public static InputStream inputStream(String s) {
        return new ByteArrayInputStream(s.getBytes());
    }

    public static String encodeURI(String src) {
        return URI.create(src).toString();
    }
}

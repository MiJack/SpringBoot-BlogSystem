package com.mijack.sbbs.utils;

import com.mijack.sbbs.model.StorageObject;
import com.youbenzi.mdtool.tool.MDTool;
import okio.ByteString;
import okio.Okio;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.security.core.Authentication;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Collection;
import java.util.List;
import java.util.regex.Pattern;

/**
 * @author Mr.Yuan
 */
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

    public static String urlDecode(String src) {
        try {
            return URLDecoder.decode(src, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            return base64Decoder(src);
        }
    }

    public static String urlEncode(String src) {
        try {
            return URLEncoder.encode(src, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            return base64Encode(src);
        }
    }

    public static String markdownSummary(String markdown) {
        Document document = Jsoup.parse(MDTool.markdown2Html(markdown));
        String text = document.text();
        if (text.length() < 200) {
            return text;
        }
        return text.substring(0, 200);
    }

    public static String string(InputStream inputStream) {
        try {
            ByteString byteString = Okio.buffer(
                    Okio.source(inputStream)).readByteString();
            return byteString.utf8();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static int size(Collection collection) {
        return collection != null ? collection.size() : 0;
    }
}

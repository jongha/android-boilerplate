package net.mrlatte.khanjar.security;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by jongha on 8/23/15.
 */
public class MD5Knife {
    public static String hex(byte[] array) {
        StringBuilder sb = new StringBuilder();
        for (byte anArray : array) {
            sb.append(Integer.toHexString((anArray & 0xFF) | 0x100).substring(1, 3));
        }
        return sb.toString();
    }

    public static String md5Hex(String message) {
        if (message != null) {
            try {
                MessageDigest md = MessageDigest.getInstance("MD5");
                return hex(md.digest(message.getBytes("CP1252")));

            } catch (NoSuchAlgorithmException | UnsupportedEncodingException ignored) {
            }
        }

        return null;
    }
}

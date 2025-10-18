package com.bodms.utils;

import org.mindrot.jbcrypt.BCrypt;

public class PasswordUtils {
    public static String hash(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt(10));
    }

    public static boolean verify(String plaintext, String hashed) {
        try {
            return BCrypt.checkpw(plaintext, hashed);
        } catch (Exception e) {
            return false;
        }
    }
}

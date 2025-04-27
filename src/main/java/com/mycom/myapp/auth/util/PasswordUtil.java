package com.mycom.myapp.auth.util;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.SecureRandom;
import java.util.Base64;

public class PasswordUtil {
    private static final int ITERATIONS = 10000;
    private static final int KEY_LENGTH = 256;

    // salt 생성
    public static String generateSalt() {
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[16];
        random.nextBytes(salt);
        return Base64.getEncoder().encodeToString(salt);
    }

    // 비밀번호 해싱
    public static String hashPassword(String password, String salt) throws Exception {
        PBEKeySpec spec = new PBEKeySpec(password.toCharArray(), Base64.getDecoder().decode(salt), ITERATIONS, KEY_LENGTH);
        SecretKeyFactory skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
        byte[] hashed = skf.generateSecret(spec).getEncoded();
        return Base64.getEncoder().encodeToString(hashed);
    }

    // 비밀번호 검증
    public static boolean verifyPassword(String inputPassword, String salt, String hashedPassword) throws Exception {
        String inputHashed = hashPassword(inputPassword, salt);
        return inputHashed.equals(hashedPassword);
    }
}

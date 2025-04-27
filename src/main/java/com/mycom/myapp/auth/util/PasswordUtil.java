package com.mycom.myapp.auth.util;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.SecureRandom;
import java.util.Base64;

public class PasswordUtil {
    private static final int ITERATIONS = 10000;
    private static final int KEY_LENGTH = 256;

    /**
     * Generates a cryptographically secure random salt encoded in Base64.
     *
     * @return a 16-byte random salt as a Base64-encoded string
     */
    public static String generateSalt() {
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[16];
        random.nextBytes(salt);
        return Base64.getEncoder().encodeToString(salt);
    }

    /**
     * Hashes a password using PBKDF2 with HMAC SHA-256 and a provided Base64-encoded salt.
     *
     * @param password the plaintext password to hash
     * @param salt the Base64-encoded salt to use in the hashing process
     * @return the Base64-encoded hashed password
     * @throws Exception if the hashing algorithm is unavailable or an error occurs during hashing
     */
    public static String hashPassword(String password, String salt) throws Exception {
        PBEKeySpec spec = new PBEKeySpec(password.toCharArray(), Base64.getDecoder().decode(salt), ITERATIONS, KEY_LENGTH);
        SecretKeyFactory skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
        byte[] hashed = skf.generateSecret(spec).getEncoded();
        return Base64.getEncoder().encodeToString(hashed);
    }

    /**
     * Verifies whether the provided password matches the stored hashed password using the given salt.
     *
     * @param inputPassword the plaintext password to verify
     * @param salt the Base64-encoded salt used during hashing
     * @param hashedPassword the stored Base64-encoded hashed password to compare against
     * @return true if the input password, when hashed with the salt, matches the stored hash; false otherwise
     * @throws Exception if hashing fails
     */
    public static boolean verifyPassword(String inputPassword, String salt, String hashedPassword) throws Exception {
        String inputHashed = hashPassword(inputPassword, salt);
        return inputHashed.equals(hashedPassword);
    }
}

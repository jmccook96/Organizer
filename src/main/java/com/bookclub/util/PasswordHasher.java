package com.bookclub.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * The {@code PasswordHasher} class is used to hash a password using the SHA-256 algorithm.
 */
public class PasswordHasher {
    /**
     * Hashes a password using the SHA-256 algorithm.
     * It takes a password as input and returns the hashed password as a string.
     *
     * @param password the password to be hashed
     * @return the hashed password as a string
     * @throws NoSuchAlgorithmException if an error occurs while hashing the password
     */
    public static String hashPassword(String password) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] encodedhash = digest.digest(password.getBytes());
            return bytesToHex(encodedhash);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Error hashing password", e);
        }
    }

    /**
     * Converts an array of bytes to a hexadecimal string representation.
     *
     * @param hash The array of bytes to be converted to hexadecimal string.
     * @return The hexadecimal string representation of the input byte array.
     */
    private static String bytesToHex(byte[] hash) {
        StringBuilder hexString = new StringBuilder(2 * hash.length);
        for (byte b : hash) {
            String hex = Integer.toHexString(0xff & b);
            if (hex.length() == 1) {
                hexString.append('0');
            }
            hexString.append(hex);
        }
        return hexString.toString();
    }
}
package com.example.util;

import com.bookclub.util.PasswordHasher;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class PasswordHasherTest {

    // Basic functionality tests
    @Test
    public void testHashPassword() {
        String password = "mySecurePassword123";
        String expectedHash = "ca6ee54120465533d367b4cac5cd2f12ee75234225130dd89470de546ab9ca46"; // Pre-calculated hash
        String actualHash = PasswordHasher.hashPassword(password);
        assertEquals(expectedHash, actualHash);
    }

    @Test
    public void testHashPasswordSpecialChars() {
        String password = "P@ssw0rd!#";
        String expectedHash = "ed27791aa8c496f0e4d9b866bae1149019e01e9de542db834e01fcc01f2752e2"; // Pre-calculated hash
        String actualHash = PasswordHasher.hashPassword(password);
        assertEquals(expectedHash, actualHash);
    }

    @Test
    public void testHashPasswordEmpty() {
        String password = "";
        String expectedHash = "e3b0c44298fc1c149afbf4c8996fb92427ae41e4649b934ca495991b7852b855"; // SHA-256 hash of an empty string
        String actualHash = PasswordHasher.hashPassword(password);
        assertEquals(expectedHash, actualHash);
    }

    // Edge case tests
    @Test
    public void testHashPasswordNull() {
        assertThrows(NullPointerException.class, () -> PasswordHasher.hashPassword(null));
    }
}

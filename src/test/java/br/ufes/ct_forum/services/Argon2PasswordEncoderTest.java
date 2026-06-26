package br.ufes.ct_forum.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class Argon2PasswordEncoderTest {
    private Argon2PasswordEncoder encoder;
    private final String testPassword = "mysupersecretpassword";

    @BeforeEach
    void beforeEach() {
        encoder = new Argon2PasswordEncoder();
    }

    @Test
    void matches() {
        String encodedPassword = encoder.encode(testPassword);
        boolean result = encoder.matches(testPassword, encodedPassword);
        assertTrue(result);
    }

    @Test
    void matchesShouldReturnFalseForWrongPassword() {
        String encodedPassword = encoder.encode(testPassword);
        String wrongPassword = "wrongpassword";
        boolean result = encoder.matches(wrongPassword, encodedPassword);
        assertFalse(result);
    }

    @Test
    void encodeShouldThrowNullPointerExceptionForNullPassword() {
        assertThrows(NullPointerException.class, () -> encoder.encode(null));
    }
}
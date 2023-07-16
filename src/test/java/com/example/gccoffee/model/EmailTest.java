package com.example.gccoffee.model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class EmailTest {
    @Test
    void testInvalidEmail() {
        assertThrows(IllegalArgumentException.class, () -> new Email("abcde"));
    }

    @Test
    void testValidEmail() {
        var email = new Email("hello@gmail.com");
        assertEquals("hello@gmail.com", email.getAddress());
    }

    @Test
    void testEqualEmail() {
        var email1 = new Email("hello@gmail.com");
        var email2 = new Email("hello@gmail.com");
        assertEquals(email1, email2);
    }
}

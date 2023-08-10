package com.example.auth_service.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import org.junit.jupiter.api.Test;

import com.example.auth_service.models.User;

class LombokConfigTest {

    @Test
    void testChainAccessors() {
        User user = new User();
        user.setId("1");
        user.setEmail("j@n.com");
        user.setPassword("jimit");
        user.setRole("admin");

        assertEquals("1", user.getId());
        assertEquals("j@n.com", user.getEmail());
        assertEquals("jimit", user.getPassword());
        assertEquals("admin", user.getRole());
    }

    @Test
    void testGeneratedAnnotation() {
        assertFalse(User.class.isAnnotationPresent(lombok.Generated.class));
    }
}

package com.example.AuthService.model;

import com.example.AuthService.models.User;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class LombokConfigTest {

    @Test
    public void testChainAccessors() {
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
    public void testGeneratedAnnotation() {
        assertFalse(User.class.isAnnotationPresent(lombok.Generated.class));
    }
}

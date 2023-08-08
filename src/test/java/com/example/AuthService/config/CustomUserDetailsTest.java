package com.example.AuthService.config;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.util.Collection;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.security.core.GrantedAuthority;
import com.example.AuthService.models.User;

public class CustomUserDetailsTest {

	@InjectMocks
	CustomUserDetails customUserDetails;
	
//    @Test
//    public void testGetAuthorities() {
//        CustomUserDetails userDetails = new CustomUserDetails(new User("test@example.com", "password"));
//
//        Collection<? extends GrantedAuthority> authorities = userDetails.getAuthorities();
//
//        assertTrue(authorities.isEmpty());
//    }

    @Test
    public void testGetPassword() {
        CustomUserDetails userDetails = new CustomUserDetails(new User("test@example.com", "password"));

        String password = userDetails.getPassword();

        assertEquals("password", password);
    }

    @Test
    public void testGetUsername() {
        CustomUserDetails userDetails = new CustomUserDetails(new User("test@example.com", "password"));

        String username = userDetails.getUsername();

        assertEquals("test@example.com", username);
    }

    @Test
    public void testAccountNonExpired() {
        CustomUserDetails userDetails = new CustomUserDetails(new User("test@example.com", "password"));

        assertTrue(userDetails.isAccountNonExpired());
    }

    @Test
    public void testAccountNonLocked() {
        CustomUserDetails userDetails = new CustomUserDetails(new User("test@example.com", "password"));

        assertTrue(userDetails.isAccountNonLocked());
    }

    @Test
    public void testCredentialsNonExpired() {
        CustomUserDetails userDetails = new CustomUserDetails(new User("test@example.com", "password"));

        assertTrue(userDetails.isCredentialsNonExpired());
    }

    @Test
    public void testIsEnabled() {
        CustomUserDetails userDetails = new CustomUserDetails(new User("test@example.com", "password"));

        assertTrue(userDetails.isEnabled());
    }
}

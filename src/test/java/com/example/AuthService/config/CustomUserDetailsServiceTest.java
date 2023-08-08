package com.example.AuthService.config;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import com.example.AuthService.models.User;
import com.example.AuthService.repositories.UserRepository;

@ExtendWith(MockitoExtension.class)
public class CustomUserDetailsServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private CustomUserDetailsService userDetailsService;

    @Test
    public void testLoadUserByUsernameUserFound() {
        User user = new User("j@n.com", "jimit", "admin");
        when(userRepository.findByEmail("j@n.com")).thenReturn(Optional.of(user));

        UserDetails userDetails = userDetailsService.loadUserByUsername("j@n.com");

        assertNotNull(userDetails);
        assertEquals("j@n.com", userDetails.getUsername());
        assertEquals("jimit", userDetails.getPassword());
    }

    @Test
    public void testLoadUserByUsernameUserNotFound() {
        when(userRepository.findByEmail("b@v.com")).thenReturn(Optional.empty());

        assertThrows(UsernameNotFoundException.class, () -> userDetailsService.loadUserByUsername("b@v.com"));
    }
}

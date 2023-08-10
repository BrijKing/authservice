package com.example.AuthService.config;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Collection;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import com.example.AuthService.models.User;

public class CustomUserDetailsTest {

	private User mockUser;

	@InjectMocks
	CustomUserDetails customUserDetails;

	@BeforeEach
	public void setUp() {
		mockUser = new User();
		mockUser.setEmail("j@n.com");
		mockUser.setPassword("jimit");
		mockUser.setRole("admin");
	}

	@Test
	public void testGetAuthorities() {
		CustomUserDetails userDetails = new CustomUserDetails(mockUser);

		Collection<? extends GrantedAuthority> authorities = userDetails.getAuthorities();

		assertEquals(1, authorities.size());
		assertTrue(authorities.contains(new SimpleGrantedAuthority("admin")));
	}

	@Test
	public void testGetPassword() {
		CustomUserDetails userDetails = new CustomUserDetails(mockUser);

		assertEquals("jimit", userDetails.getPassword());
	}

	@Test
	public void testGetUsername() {
		CustomUserDetails userDetails = new CustomUserDetails(mockUser);

		assertEquals("j@n.com", userDetails.getUsername());
	}

	@Test
	public void testIsAccountNonExpired() {
		CustomUserDetails userDetails = new CustomUserDetails(mockUser);

		assertTrue(userDetails.isAccountNonExpired());
	}

	@Test
	public void testIsAccountNonLocked() {
		CustomUserDetails userDetails = new CustomUserDetails(mockUser);

		assertTrue(userDetails.isAccountNonLocked());
	}

	@Test
	public void testIsCredentialsNonExpired() {
		CustomUserDetails userDetails = new CustomUserDetails(mockUser);

		assertTrue(userDetails.isCredentialsNonExpired());
	}

	@Test
	public void testIsEnabled() {
		CustomUserDetails userDetails = new CustomUserDetails(mockUser);

		assertTrue(userDetails.isEnabled());
	}
}
package com.example.AuthService.Controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;

import com.example.AuthService.CustomExceptions.UnauthorizedUserException;
import com.example.AuthService.config.CustomUserDetails;
import com.example.AuthService.controller.UserController;
import com.example.AuthService.dto.AuthRequest;
import com.example.AuthService.models.User;
import com.example.AuthService.services.UserService;

@ExtendWith(MockitoExtension.class)
public class UserControllerTest {

	@Mock
	private UserService userService;

	@Mock
	private AuthenticationManager authenticationManager;

	@InjectMocks
	private UserController userController;

	@Test
	public void testRegisterUser() {
		User user = new User("j@n.com", "jimit", "admin");
		when(userService.registerUser(user)).thenReturn("User registered successfully");

		ResponseEntity<String> response = userController.registerUser(user);

		assertEquals("User registered successfully", response.getBody());
		assertEquals(HttpStatus.CREATED, response.getStatusCode());
	}

	@Test
	public void testGenerateTokenAuthenticated() throws UnauthorizedUserException {
		AuthRequest authRequest = new AuthRequest("j@n.com", "jimit");
		UserDetails userDetails = new CustomUserDetails(new User("j@n.com", "jimit"));

		when(authenticationManager.authenticate(any(Authentication.class)))
				.thenReturn(new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities()));
		when(userService.generateToken(authRequest.getEmail())).thenReturn("generated-token");

		ResponseEntity<String> response = userController.generateToken(authRequest);

		assertEquals("generated-token", response.getBody());
		assertEquals(HttpStatus.CREATED, response.getStatusCode());

		verify(authenticationManager, times(1)).authenticate(any(Authentication.class));
		verify(userService, times(1)).generateToken(authRequest.getEmail());
	}

	@Test
	public void testGenerateTokenUnauthenticated() throws UnauthorizedUserException {
		AuthRequest authRequest = new AuthRequest("j@n.com", "jimit");

		when(authenticationManager.authenticate(any(Authentication.class)))
				.thenThrow(new BadCredentialsException("Bad credentials"));

		ResponseEntity<String> response = userController.generateToken(authRequest);

		assertEquals("Unauthenticated user", response.getBody());
		assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());

		verify(authenticationManager, times(1)).authenticate(any(Authentication.class));
		verifyNoInteractions(userService);
	}

	@Test
	public void testValidateTokenValid() {
		String token = "valid-token";
		ResponseEntity<String> expectedResponse = new ResponseEntity<>("valid token", HttpStatus.OK);

		when(userService.validateToken(token)).thenReturn(expectedResponse);

		ResponseEntity<String> response = userController.validateToken(token);

		assertEquals(expectedResponse, response);
	}

	@Test
	public void testValidateTokenInvalid() {
		String token = "invalid-token";
		ResponseEntity<String> expectedResponse = new ResponseEntity<>("Invalid Token received!!",
				HttpStatus.UNAUTHORIZED);

		when(userService.validateToken(token)).thenReturn(expectedResponse);

		ResponseEntity<String> response = userController.validateToken(token);

		assertEquals(expectedResponse, response);
	}

}

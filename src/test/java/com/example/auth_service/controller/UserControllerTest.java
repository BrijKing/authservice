package com.example.auth_service.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import com.example.auth_service.custom_exceptions.UnauthorizedUserException;
import com.example.auth_service.dto.AuthRequest;
import com.example.auth_service.models.User;
import com.example.auth_service.services.UserService;

@ExtendWith(MockitoExtension.class)
class UserControllerTest {

	@Mock
	private UserService userService;

	@Mock
	private AuthenticationManager authenticationManager;

	@InjectMocks
	private UserController userController;

	@Test
	void testRegisterUser() {
		User user = new User("j@n.com", "jimit", "admin");
		when(userService.registerUser(user)).thenReturn("User registered successfully");

		ResponseEntity<String> response = userController.registerUser(user);

		assertEquals("User registered successfully", response.getBody());
		assertEquals(HttpStatus.CREATED, response.getStatusCode());
	}

	@Test
	void testGenerateToken() throws UnauthorizedUserException {

		AuthRequest mockAuthRequest = new AuthRequest();
		mockAuthRequest.setEmail("j@n.com");
		mockAuthRequest.setPassword("jimt");

		AuthenticationManager authenticationManager = mock(AuthenticationManager.class);
		UserController userController = new UserController();
		userController.setAuthenticationManager(authenticationManager);

		Authentication mockAuthentication = mock(Authentication.class);
		when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
				.thenReturn(mockAuthentication);
		when(mockAuthentication.isAuthenticated()).thenReturn(true);

		UserService userService = mock(UserService.class);
		when(userService.generateToken("j@n.com")).thenReturn("token");
		userController.setUserService(userService);

		ResponseEntity<String> response = userController.generateToken(mockAuthRequest);

		assertEquals(HttpStatus.CREATED, response.getStatusCode());
		assertEquals("token", response.getBody());
	}

	@Test
	void testValidateTokenValid() {
		String token = "valid-token";
		ResponseEntity<String> expectedResponse = new ResponseEntity<>("valid token", HttpStatus.OK);

		when(userService.validateToken(token)).thenReturn(expectedResponse);

		ResponseEntity<String> response = userController.validateToken(token);

		assertEquals(expectedResponse, response);
	}

	@Test
	void testValidateTokenInvalid() {
		String token = "invalid-token";
		ResponseEntity<String> expectedResponse = new ResponseEntity<>("Invalid Token received!!",
				HttpStatus.UNAUTHORIZED);

		when(userService.validateToken(token)).thenReturn(expectedResponse);

		ResponseEntity<String> response = userController.validateToken(token);

		assertEquals(expectedResponse, response);
	}

}

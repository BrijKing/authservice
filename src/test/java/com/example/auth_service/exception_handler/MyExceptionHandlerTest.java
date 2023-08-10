package com.example.AuthService.ExceptionHandler;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.example.AuthService.CustomExceptions.InvalidTokenException;
import com.example.AuthService.CustomExceptions.UnauthorizedUserException;

@ExtendWith(MockitoExtension.class)
public class MyExceptionHandlerTest {

	@Mock
	private InvalidTokenException invalidTokenException;
	
	@Mock
	private UnauthorizedUserException unauthorizedUserException;

	@InjectMocks
	private MyExceptionHandler myExceptionHandler;

	@Test
	public void testInvalidToken() {
		when(invalidTokenException.getMessage()).thenReturn("Invalid token message");
		
	    ResponseEntity<String> response = myExceptionHandler.invalidToken(invalidTokenException);

        assertEquals("Invalid token message", response.getBody());
        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
    }
	
	@Test
	public void testUnAuthorized() {
		when(unauthorizedUserException.getMessage()).thenReturn("user is not authenticated");
		
		ResponseEntity<String> response = myExceptionHandler.unAuthorized(unauthorizedUserException);
		
		assertEquals("user is not authenticated", response.getBody());
        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
	}
}

package com.example.auth_service.exception_handler;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.example.auth_service.custom_exceptions.InvalidTokenException;
import com.example.auth_service.custom_exceptions.UnauthorizedUserException;
import com.example.auth_service.custom_exceptions.UserEmailNotFoundException;

@ExtendWith(MockitoExtension.class)
class MyExceptionHandlerTest {

	@Mock
	private InvalidTokenException invalidTokenException;
	
	@Mock
	private UnauthorizedUserException unauthorizedUserException;
	
	@Mock
	private UserEmailNotFoundException userEmailNotFoundException;

	@InjectMocks
	private MyExceptionHandler myExceptionHandler;

	@Test
	void testInvalidToken() {
		when(invalidTokenException.getMessage()).thenReturn("Invalid token message");
		
	    ResponseEntity<String> response = myExceptionHandler.invalidToken(invalidTokenException);

        assertEquals("Invalid token message", response.getBody());
        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
    }
	
	@Test
	void testUnAuthorized() {
		when(unauthorizedUserException.getMessage()).thenReturn("user is not authenticated");
		
		ResponseEntity<String> response = myExceptionHandler.unAuthorized(unauthorizedUserException);
		
		assertEquals("user is not authenticated", response.getBody());
        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
	}
	
	@Test
	void testEmailNotFound() {
		when(userEmailNotFoundException.getMessage()).thenReturn("Employee  not found");
		
		ResponseEntity<String> response = myExceptionHandler.emailNotFound(userEmailNotFoundException);
		
		assertEquals("Employee  not found", response.getBody());
        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());

	}
}

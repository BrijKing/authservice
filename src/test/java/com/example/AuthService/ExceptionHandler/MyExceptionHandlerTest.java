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

@ExtendWith(MockitoExtension.class)
public class MyExceptionHandlerTest {
	
	@Mock
	private InvalidTokenException invalidTokenException;

	@InjectMocks
	private MyExceptionHandler myExceptionHandler;

	@Test
	    public void testMymessage() {
	        when(invalidTokenException.getMessage()).thenReturn("Invalid token message");

	        ResponseEntity<String> response = myExceptionHandler.Mymessage(invalidTokenException);

	        assertEquals("Invalid token message", response.getBody());
	        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
	    }
}

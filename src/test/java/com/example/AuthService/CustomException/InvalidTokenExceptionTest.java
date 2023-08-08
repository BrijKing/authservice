package com.example.AuthService.CustomException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;
import com.example.AuthService.CustomExceptions.InvalidTokenException;

public class InvalidTokenExceptionTest {

	@Test
	public void testExceptionMessage() {
		InvalidTokenException exception = new InvalidTokenException();

		assertEquals("Invalid Token received :( ", exception.getMessage());
	}
}

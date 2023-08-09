package com.example.AuthService.CustomException;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import com.example.AuthService.CustomExceptions.UnauthorizedUserException;

public class UnauthorizedUserExceptionTest {

	@Test
	public void testExceptionMessage() {
		UnauthorizedUserException exception = new UnauthorizedUserException();

		assertEquals("user is not authenticated", exception.getMessage());
	}
}

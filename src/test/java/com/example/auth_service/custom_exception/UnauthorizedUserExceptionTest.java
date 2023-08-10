package com.example.auth_service.custom_exception;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import com.example.auth_service.custom_exceptions.UnauthorizedUserException;

class UnauthorizedUserExceptionTest {

	@Test
	void testExceptionMessage() {
		UnauthorizedUserException exception = new UnauthorizedUserException();

		assertEquals("user is not authenticated", exception.getMessage());
	}
}

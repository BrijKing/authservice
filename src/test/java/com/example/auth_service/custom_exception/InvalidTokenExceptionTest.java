package com.example.auth_service.custom_exception;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import com.example.auth_service.custom_exceptions.InvalidTokenException;

class InvalidTokenExceptionTest {

	@Test
	void testExceptionMessage() {
		InvalidTokenException exception = new InvalidTokenException();

		assertEquals("Invalid Token received :( ", exception.getMessage());
	}
}

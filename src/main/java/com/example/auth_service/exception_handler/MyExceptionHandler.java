package com.example.auth_service.exception_handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.example.auth_service.custom_exceptions.InvalidTokenException;
import com.example.auth_service.custom_exceptions.UnauthorizedUserException;
import com.example.auth_service.custom_exceptions.UserEmailNotFoundException;

@RestControllerAdvice
public class MyExceptionHandler {
	

	@ExceptionHandler(InvalidTokenException.class)
	public ResponseEntity<String> invalidToken(InvalidTokenException c){
		return new ResponseEntity<>(c.getMessage(),HttpStatus.UNAUTHORIZED);
	}
	

	@ExceptionHandler(UnauthorizedUserException.class)
	public ResponseEntity<String> unAuthorized(UnauthorizedUserException c){
		return new ResponseEntity<>(c.getMessage(),HttpStatus.UNAUTHORIZED);
	}

	@ExceptionHandler(UserEmailNotFoundException.class)
	public ResponseEntity<String> emailNotFound(UserEmailNotFoundException c){
		return new ResponseEntity<>(c.getMessage(),HttpStatus.UNAUTHORIZED);
	}
}

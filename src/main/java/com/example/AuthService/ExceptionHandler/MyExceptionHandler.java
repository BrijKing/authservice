package com.example.AuthService.ExceptionHandler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.example.AuthService.CustomExceptions.InvalidTokenException;
import com.example.AuthService.CustomExceptions.UnauthorizedUserException;

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

}

package com.example.auth_service.custom_exceptions;

public class UnauthorizedUserException extends Exception{
	
	public UnauthorizedUserException() {
		super("user is not authenticated");
	}

}

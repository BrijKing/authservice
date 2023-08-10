package com.example.AuthService.CustomExceptions;

public class UnauthorizedUserException extends Exception{
	
	public UnauthorizedUserException() {
		super("user is not authenticated");
	}

}

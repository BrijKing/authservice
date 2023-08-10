package com.example.auth_service.custom_exceptions;

@SuppressWarnings("serial")
public class InvalidTokenException  extends Exception {
	
	public InvalidTokenException() {
		super("Invalid Token received :( ");
	}
}

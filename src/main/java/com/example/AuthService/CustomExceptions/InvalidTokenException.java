package com.example.AuthService.CustomExceptions;

@SuppressWarnings("serial")
public class InvalidTokenException  extends Exception {
	
	public InvalidTokenException() {
		// TODO Auto-generated constructor stub
		super("Invalid Token received :( ");
	}
}

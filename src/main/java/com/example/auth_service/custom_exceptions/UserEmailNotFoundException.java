package com.example.auth_service.custom_exceptions;

public class UserEmailNotFoundException extends Exception{
	
	public UserEmailNotFoundException() {
		super("Employee  not found");
	}

}

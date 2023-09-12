package com.example.auth_service.custom_exceptions;

@SuppressWarnings("serial")
public class RefreshTokenNotFoundException extends Exception{

	public RefreshTokenNotFoundException() {
		super("Refresh Token is not found for this user");
	}
	
}

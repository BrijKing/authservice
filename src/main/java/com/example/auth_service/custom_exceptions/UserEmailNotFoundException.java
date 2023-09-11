package com.example.auth_service.custom_exceptions;

public class UserEmailNotFoundException extends Exception{

	private static final long serialVersionUID = 1L;

		public UserEmailNotFoundException() {
			super("Employee  not found");
		}

}

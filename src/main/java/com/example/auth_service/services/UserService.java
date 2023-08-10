package com.example.auth_service.services;

import org.springframework.http.ResponseEntity;

import com.example.auth_service.models.User;

public interface UserService {

	String registerUser(User user);
	
    String generateToken(String email);
    
    ResponseEntity<String> validateToken(String token);
	
	
	
}

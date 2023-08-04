package com.example.AuthService.services;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.example.AuthService.models.User;

public interface UserService {

	String registerUser(User user);
	
    String generateToken(String email);
    
    ResponseEntity<String> validateToken(String token);
	
	
	
}

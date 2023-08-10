package com.example.auth_service.services;

import java.security.Key;
import java.util.Map;

import org.springframework.http.ResponseEntity;

public interface JwtService {
	
	ResponseEntity<String> validateToken(final String token);
	
	String generateToken(String email);
	
	String createToken(Map<String, Object> claims, String email);
	
	Key getSignKey();

}  

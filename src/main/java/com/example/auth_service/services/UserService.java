package com.example.auth_service.services;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import com.example.auth_service.custom_exceptions.UserEmailNotFoundException;
import com.example.auth_service.dto.EmployeeDto;
import com.example.auth_service.models.User;


public interface UserService {

	String registerUser(User user);
	
    String generateToken(String email);
    
    ResponseEntity<String> validateToken(String token);
    
    List<User> getAllUsers();
	
	Page<EmployeeDto> getPaginatedResults(int page);
	
	List<EmployeeDto> findUserByEmail(String email) throws UserEmailNotFoundException;
	
	
	
}

package com.example.AuthService.services;

import java.util.List;

import com.example.AuthService.models.User;

public interface UserService {

	User addUser(User user);
	
	List<User> getAllUser();
	
	User getUserByEmail(String email) throws Exception;
	
	void deleteUserByEmail(String email) throws Exception;
	
	
	
}

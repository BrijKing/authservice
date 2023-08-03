package com.example.AuthService.controller;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.AuthService.models.User;
import com.example.AuthService.services.UserService;

@RestController
@RequestMapping("auth/user")
public class UserController {
	
	@Autowired
	UserService userService;
	
	
	@PostMapping("/add")
	public ResponseEntity<User> addUser(@RequestBody User user){
		
		return new ResponseEntity<>(userService.addUser(user),HttpStatus.CREATED);
		
		
	}
	
	
	

}

package com.example.AuthService.controller;




import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.AuthService.dto.AuthRequest;
import com.example.AuthService.models.User;
import com.example.AuthService.services.UserService;

@RestController
@RequestMapping("auth/user")
public class UserController {
	
	@Autowired
	UserService userService;
	
	@Autowired
	AuthenticationManager authenticationManager;
	
	
	@PostMapping("/register")
	public ResponseEntity<String> registerUser(@RequestBody User user){
		
		return new ResponseEntity<>(userService.registerUser(user),HttpStatus.CREATED);
		
	}
	
	@PostMapping("/token")
	public ResponseEntity<String> generateToken(@RequestBody AuthRequest authRequest){
		
		Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getEmail(), authRequest.getPassword()));
		
		if(authenticate.isAuthenticated()) {
			return new ResponseEntity<String>(userService.generateToken(authRequest.getEmail()),HttpStatus.CREATED);
		}
		else {
			return new ResponseEntity<String>(new RuntimeException("Unauthenticated user").getMessage(),HttpStatus.UNAUTHORIZED);
		}
	}
	
	  @GetMapping("/validate")
	    public ResponseEntity<String> validateToken(@RequestParam("token") String token) {
	       return userService.validateToken(token);
	       
	    }
	
	
	

}

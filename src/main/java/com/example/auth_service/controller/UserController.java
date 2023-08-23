package com.example.auth_service.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.auth_service.custom_exceptions.UnauthorizedUserException;
import com.example.auth_service.custom_exceptions.UserEmailNotFoundException;
import com.example.auth_service.dto.AuthRequest;
import com.example.auth_service.dto.UserDto;
import com.example.auth_service.models.User;
import com.example.auth_service.services.UserService;

import jakarta.ws.rs.GET;

@RestController
@RequestMapping("auth/user")
public class UserController {

	@Autowired
	UserService userService;

	@Autowired
	AuthenticationManager authenticationManager;
	
	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	public void setAuthenticationManager(AuthenticationManager authenticationManager) {
		this.authenticationManager = authenticationManager;
	}

	@PostMapping("/register")
	public ResponseEntity<String> registerUser(@RequestBody UserDto userDto) {

		User user =  new User();
		user.setEmail(userDto.getEmail());
		user.setPassword(userDto.getPassword());
		user.setRole(userDto.getRole());
		return new ResponseEntity<>(userService.registerUser(user), HttpStatus.CREATED);

	}

	@PostMapping("/token")
	public ResponseEntity<String> generateToken(@RequestBody AuthRequest authRequest) throws UnauthorizedUserException {

		Authentication authenticate = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(authRequest.getEmail(), authRequest.getPassword()));

		if (!authenticate.isAuthenticated())
			throw new UnauthorizedUserException();

		return new ResponseEntity<>(userService.generateToken(authRequest.getEmail()), HttpStatus.CREATED);

	}

	@GetMapping("/validate")
	public ResponseEntity<String> validateToken(@RequestParam("token") String token) {
		return userService.validateToken(token);

	}
	
	@GetMapping("/getAll")
	public ResponseEntity<Page<User>> getAllUser(@RequestParam int pageNo){
		return new ResponseEntity<>(userService.getPaginatedResults(pageNo), HttpStatus.OK);
	}
	
	@GetMapping("/findUser/{email}")
	public ResponseEntity<List<User>> findUser(@PathVariable String email) throws UserEmailNotFoundException{
		return new ResponseEntity<>(userService.findUserByEmai(email),HttpStatus.OK);
	}
	
	
	

}

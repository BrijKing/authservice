package com.example.auth_service.service_impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.auth_service.custom_exceptions.UserEmailNotFoundException;
import com.example.auth_service.models.User;
import com.example.auth_service.repositories.UserRepository;
import com.example.auth_service.services.JwtService;
import com.example.auth_service.services.UserService;

@Service
public class UserServiceImp implements UserService {
	
	@Autowired
    private UserRepository userRepository;
	
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtService jwtService;

	public UserServiceImp(UserRepository userRepository, PasswordEncoder passwordEncoder) {
		super();
		this.userRepository = userRepository;
		this.passwordEncoder = passwordEncoder;
	}

	@Override
	public String registerUser(User user) {
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		userRepository.save(user);
		return "user registered successfully!!!";
	}

	@Override
	public String generateToken(String email) {
		return jwtService.generateToken(email);
	}

	@Override
	public ResponseEntity<String> validateToken(String token) {
		return jwtService.validateToken(token);
	}

	@Override
	public List<User> getAllUsers() {
		
		return userRepository.findAll();
	}

	@Override
	public Page<User> getPaginatedResults(int page) {
		Pageable pageable = PageRequest.of(page, 5);
		
		return userRepository.findAll(pageable) ;
	}

	@Override
	public List<User> findUserByEmai(String email) throws UserEmailNotFoundException {
		
	User user  = userRepository.findByEmail(email).orElseThrow(() -> new UserEmailNotFoundException());
	
	List<User> userList = new ArrayList<>();
	
	userList.add(user);
	
	
		return userList;
	}
}
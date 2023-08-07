package com.example.AuthService.ServiceImp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.AuthService.models.User;
import com.example.AuthService.repositories.UserRepository;
import com.example.AuthService.services.JwtService;
import com.example.AuthService.services.UserService;

@Service
public class UserServiceImp implements UserService {
	
	@Autowired
    private UserRepository userRepository;
	
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtService jwtService;

	@Override
	public String registerUser(User user) {
		// TODO Auto-generated method stub
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		userRepository.save(user);
		return "user registered successfully!!!";
	}

	@Override
	public String generateToken(String email) {
		// TODO Auto-generated method stub
		return jwtService.generateToken(email);
	}

	@Override
	public ResponseEntity<String> validateToken(String token) {
		// TODO Auto-generated method stub
		return jwtService.validateToken(token);

	}

}

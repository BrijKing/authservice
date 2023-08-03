package com.example.AuthService.ServiceImp;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.AuthService.models.User;
import com.example.AuthService.repositories.UserRepository;
import com.example.AuthService.services.UserService;


@Service
public class UserServiceImp implements UserService {
	
	
	@Autowired
	UserRepository userRepository;

	@Override
	public User addUser(User user) {
		// TODO Auto-generated method stub
		return userRepository.save(user);
	}

	@Override
	public List<User> getAllUser() {
		// TODO Auto-generated method stub
		return userRepository.findAll();
	}

	@Override
	public User getUserByEmail(String email) throws Exception {
		// TODO Auto-generated method stub
		
		return userRepository.findByEmail(email).orElseThrow(() -> new Exception("user not found with email : " + email));
	}

	@Override
	public void deleteUserByEmail(String email) throws Exception {
		// TODO Auto-generated method stub
		
		  Optional<User> user = userRepository.findByEmail(email);
        if (user.isPresent()) {
            userRepository.deleteByEmail(email);;
        } 
        else {
        	throw new Exception("user not found with email: " + email);
        }
		
		
		 
		
	}

}

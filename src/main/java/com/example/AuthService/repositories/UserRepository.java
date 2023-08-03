package com.example.AuthService.repositories;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.example.AuthService.models.User;

public interface UserRepository extends MongoRepository<User, String>{
	
	Optional<User> findByEmail(String email);
	
	void deleteByEmail(String email);
	
    boolean existsByEmail(String email);
}

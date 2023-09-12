package com.example.auth_service.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.example.auth_service.models.RefreshToken;

public interface RefreshTokenRepository extends MongoRepository<RefreshToken, String>{

	
}

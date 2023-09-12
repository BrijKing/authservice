package com.example.auth_service.service_impl;

import java.time.Instant;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.auth_service.custom_exceptions.RefreshTokenNotFoundException;
import com.example.auth_service.custom_exceptions.UserEmailNotFoundException;
import com.example.auth_service.models.RefreshToken;
import com.example.auth_service.repositories.RefreshTokenRepository;
import com.example.auth_service.repositories.UserRepository;
import com.example.auth_service.services.RefreshTokenService;

@Service
public class RefreshTokenServiceImp implements RefreshTokenService {

	@Autowired
	private RefreshTokenRepository refreshTokenRepository;
	
	@Autowired
	private UserRepository userRepository;

	public RefreshToken createFreshToken(String email) throws UserEmailNotFoundException {

		return RefreshToken.builder()
			.refreshToken(UUID.randomUUID().toString())
			.expiry(Instant.now().plusMillis(5 * 60 * 60 * 1000))
			.user(userRepository.findByEmail(email).orElseThrow(UserEmailNotFoundException::new))
			.build();
		
	}

	public boolean verifyRefreshToken(String refreshToken) throws Exception {
		
		RefreshToken refreshToken2 = refreshTokenRepository.findById(refreshToken).orElseThrow(RefreshTokenNotFoundException::new);
		
		if (refreshToken2.getExpiry().compareTo(Instant.now()) < 0) {
			throw new Exception();
		}
		return false;
	}

}

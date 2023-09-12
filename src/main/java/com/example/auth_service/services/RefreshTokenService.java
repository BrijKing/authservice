package com.example.auth_service.services;

import com.example.auth_service.custom_exceptions.RefreshTokenNotFoundException;
import com.example.auth_service.custom_exceptions.UserEmailNotFoundException;
import com.example.auth_service.models.RefreshToken;

public interface RefreshTokenService {

	public RefreshToken createFreshToken(String email) throws UserEmailNotFoundException;
	
	public boolean verifyRefreshToken(String refreshToken) throws RefreshTokenNotFoundException, Exception;
}

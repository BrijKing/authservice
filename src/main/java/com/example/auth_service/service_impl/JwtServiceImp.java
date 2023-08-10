package com.example.auth_service.service_impl;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.auth_service.models.User;
import com.example.auth_service.repositories.UserRepository;
import com.example.auth_service.services.JwtService;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtServiceImp implements JwtService {

	@Autowired
	private UserRepository userRepository;

	public void setUserRepository(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	public static final String SECRET = "5367566B59703373367639792F423F4528482B4D6251655468576D5A71347437";

	@Override
	public ResponseEntity<String> validateToken(String token) {

		try {

			Jwts.parserBuilder().setSigningKey(getSignKey()).build().parseClaimsJws(token).toString();

			return new ResponseEntity<>("valid token", HttpStatus.OK);

		} catch (ExpiredJwtException e) {

			return new ResponseEntity<>("your login session has been expired please login again !!",
					HttpStatus.UNAUTHORIZED);
		} catch (Exception e) {

			return new ResponseEntity<>("Invalid Token received!!" + e.getMessage(), HttpStatus.UNAUTHORIZED);
		}
	}

	@Override
	public String generateToken(String email) {

		Map<String, Object> claims = new HashMap<>();
		return createToken(claims, email);
	}

	@Override
	public String createToken(Map<String, Object> claims, String email) {

		return Jwts.builder().setClaims(claims).setSubject(email).claim("authorities", populateAuthorities(email))
				.setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 30))
				.signWith(getSignKey(), SignatureAlgorithm.HS256).compact();
	}

	@Override
	public Key getSignKey() {

		byte[] keyBytes = Decoders.BASE64.decode(SECRET);
		return Keys.hmacShaKeyFor(keyBytes);
	}

	public String populateAuthorities(String email) {

		Optional<User> userInfo = userRepository.findByEmail(email);
		
		if (userInfo.isEmpty()) {
			return "user not found";
		}
		return userInfo.get().getRole();
	}

}

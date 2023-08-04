package com.example.AuthService.ServiceImp;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.AuthService.CustomExceptions.InvalidTokenException;
import com.example.AuthService.services.JwtService;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtServiceImp implements JwtService {

	public static final String SECRET = "5367566B59703373367639792F423F4528482B4D6251655468576D5A71347437";

	@Override
	public ResponseEntity<String> validateToken(String token) {
		// TODO Auto-generated method stub
		try {
			
		
		Jwts.parserBuilder().setSigningKey(getSignKey()).build().parseClaimsJws(token).toString();
		
		return new ResponseEntity<String>("valid token",HttpStatus.OK);
		
		}catch (Exception e) {
			// TODO: handle exception
			return new ResponseEntity<String>("Invalid Token received!!" + e.getMessage(),HttpStatus.UNAUTHORIZED);
		}
	}

	@Override
	public String generateToken(String email) {
		// TODO Auto-generated method stub
		Map<String, Object> claims = new HashMap<>();
		return createToken(claims, email);
	}

	@Override
	public String createToken(Map<String, Object> claims, String email) {
		// TODO Auto-generated method stub
		return Jwts.builder()
        .setClaims(claims)
        .setSubject(email)
        .setIssuedAt(new Date(System.currentTimeMillis()))
        .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 30))
        .signWith(getSignKey(), SignatureAlgorithm.HS256).compact();
	}

	@Override
	public Key getSignKey() {
		// TODO Auto-generated method stub
		byte[] keyBytes = Decoders.BASE64.decode(SECRET);
        return Keys.hmacShaKeyFor(keyBytes);
	}

}

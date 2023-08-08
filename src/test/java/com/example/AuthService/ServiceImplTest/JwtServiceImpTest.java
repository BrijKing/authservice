package com.example.AuthService.ServiceImplTest;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.security.Key;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.SecretKey;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.example.AuthService.ServiceImp.JwtServiceImp;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class JwtServiceImpTest {

	@InjectMocks
	private JwtServiceImp jwtServiceImp;

	private final String SECRET = "5367566B59703373367639792F423F4528482B4D6251655468576D5A71347437";

	private String generateValidToken() {
		Key key = jwtServiceImp.getSignKey();
		return Jwts.builder().setSubject("testSubject").setExpiration(new Date(System.currentTimeMillis() + 3600000))
				.signWith(key).compact();
	}

	private String generateExpiredToken() {
		Key key = jwtServiceImp.getSignKey();
		return Jwts.builder().setSubject("testSubject").setExpiration(new Date(System.currentTimeMillis() - 3600000))
				.signWith(key).compact();
	}

	@Test
	public void testValidToken() {
		String validToken = generateValidToken();

		ResponseEntity<String> response = jwtServiceImp.validateToken(validToken);
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals("valid token", response.getBody());
	}

	@Test
	public void testInvalidToken() {
		String invalidToken = "invalid_token";

		ResponseEntity<String> response = jwtServiceImp.validateToken(invalidToken);
		assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
		assertEquals("Invalid Token received!!JWT strings must contain exactly 2 period characters. Found: 0",
				response.getBody());
	}

	@Test
	public void testExpiredToken() {
		String expiredToken = generateExpiredToken();
		ResponseEntity<String> response = jwtServiceImp.validateToken(expiredToken);
		assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
//		assertEquals(
//				"Invalid Token received!!JWT expired at 2023-08-08T04:26:35Z. Current time: 2023-08-08T05:26:35Z, a difference of 3600541 milliseconds.  Allowed clock skew: 0 milliseconds.",
//				response.getBody());
	}

	@Test
	public void testGenerateToken() {
		String email = "j@n.com";
		String generatedToken = jwtServiceImp.generateToken(email);

		assertNotNull(generatedToken);
		Claims claims = Jwts.parserBuilder().setSigningKey(jwtServiceImp.getSignKey()).build().parseClaimsJws(generatedToken)
				.getBody();
		assertEquals(email, claims.getSubject());
	}

	@Test
	public void testCreateToken() {
		String email = "j@n.com";
		String generatedToken = jwtServiceImp.createToken(new HashMap<>(), email);

		assertNotNull(generatedToken);
		Claims claims = Jwts.parserBuilder().setSigningKey(jwtServiceImp.getSignKey()).build().parseClaimsJws(generatedToken)
				.getBody();
		assertEquals(email, claims.getSubject());
	}

	@Test
	public void testGetSignKey() {

		Key generatedKey = jwtServiceImp.getSignKey();
		assertNotNull(generatedKey);

		byte[] expectedKeyBytes = Base64.getDecoder().decode(SECRET);
		byte[] actualKeyBytes = generatedKey.getEncoded();

		assertArrayEquals(expectedKeyBytes, actualKeyBytes);
	}
}

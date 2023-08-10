package com.example.auth_service.service_impl_test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.example.auth_service.models.User;
import com.example.auth_service.repositories.UserRepository;
import com.example.auth_service.service_impl.UserServiceImp;
import com.example.auth_service.services.JwtService;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

	@Mock
	private PasswordEncoder passwordEncoder;

	@Mock
	private UserRepository userRepository;

	@Mock
	private JwtService jwtService;

	@InjectMocks
	private UserServiceImp userService;

	private User user;

	@BeforeEach
	void setup() {
		user = new User("10", "j@n.com", "jimit", "admin");
	}

	@Test
    void testRegisterUser() {

        when(passwordEncoder.encode(user.getPassword())).thenReturn("jimit");

        String result = userService.registerUser(user);

        verify(passwordEncoder).encode(user.getPassword()); 
        verify(userRepository).save(user);

        assertEquals("user registered successfully!!!", result);
        assertEquals("jimit", user.getPassword()); 
	}

	@Test
	void testGenerateToken() {

		String expectedToken = "qwertyuiopzasxcfvgbhnjmkl";
		when(jwtService.generateToken(user.getEmail())).thenReturn(expectedToken);

		String generatedToken = userService.generateToken(user.getEmail());
		assertEquals(expectedToken, generatedToken);
	}

	@Test
	void testValidateValidToken() {

		String validToken = "validToken";

		when(jwtService.validateToken(validToken)).thenReturn(ResponseEntity.ok("Token is valid."));
		ResponseEntity<String> response = userService.validateToken(validToken);

		verify(jwtService).validateToken(validToken);

		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals("Token is valid.", response.getBody());
	}

	@Test
	void testValidateInvalidToken() {

		String invalidToken = "invalidToken";

		when(jwtService.validateToken(invalidToken))
				.thenReturn(ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Token is invalid."));

		ResponseEntity<String> response = userService.validateToken(invalidToken);

		verify(jwtService).validateToken(invalidToken);

		assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
		assertEquals("Token is invalid.", response.getBody());
	}
}

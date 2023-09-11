package com.example.auth_service.service_impl_test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.example.auth_service.custom_exceptions.UserEmailNotFoundException;
import com.example.auth_service.dto.EmployeeDto;
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

	@Mock
	private Pageable pageable;

	@InjectMocks
	private UserServiceImp userService;

	private User user, user1, user2;

	private List<User> allUsers;

	@BeforeEach
	void setup() {
		user = new User("10", "j@n.com", "jimit", "admin");
		user1 = new User("11", "b@v.com", "brijesh", "Reviewer");
		user2 = new User("12", "p@s.com", "part", "agent");
		allUsers = Arrays.asList(user, user1, user2);
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

	@Test
	void testGetAllUsers() {

	        when(userRepository.findAll()).thenReturn(allUsers);

	        List<User> result = userService.getAllUsers();

	        assertEquals(allUsers, result);
	}

	@Test
	void testGetPaginatedResults() {

		Pageable pageable = PageRequest.of(0, 5);
		Page<User> page = new PageImpl<>(allUsers, pageable, allUsers.size());
		when(userRepository.findAll(pageable)).thenReturn(page);

		Page<EmployeeDto> paginatedResults = userService.getPaginatedResults(0);
		assertEquals(allUsers.size(), paginatedResults.getTotalElements());
		assertEquals(allUsers.size(), paginatedResults.getNumberOfElements());
	}

	@Test
	void testFindUserByEmail() throws UserEmailNotFoundException{
			
		when(userRepository.findByEmail(user.getEmail())).thenReturn(Optional.of(user));
		
		List<EmployeeDto> findUserByEmail = userService.findUserByEmail(user.getEmail());
		
		EmployeeDto empDto = findUserByEmail.get(0);
		assertEquals(user.getEmail(), empDto.getEmail());	
	}
	
	@Test
	void testFindUserByEmailNotFound() {
		
		when(userRepository.findByEmail(user.getEmail())).thenReturn(Optional.empty());
		
		Exception exception = assertThrows(UserEmailNotFoundException.class, () ->{userService.findUserByEmail(user.getEmail());});
		
		assertEquals("Employee  not found", exception.getMessage());
	}

}

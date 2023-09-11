package com.example.auth_service.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.List;

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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.example.auth_service.custom_exceptions.UserEmailNotFoundException;
import com.example.auth_service.dto.EmployeeDto;
import com.example.auth_service.services.UserService;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
class EmployeeControllerTest {

	@Mock
	private UserService userService;

	@InjectMocks
	EmployeeController employeeController;

	private EmployeeDto employeeDto;

	
	@BeforeEach
	void setUp() {
		employeeDto = new EmployeeDto("1", "j@n.com", "admin");
	}

	@Test
	void testGetAllUser() {
		Page<EmployeeDto> page = new PageImpl<>(List.of(employeeDto), PageRequest.of(0, 5), 1);

		when(userService.getPaginatedResults(0)).thenReturn(page);

		ResponseEntity<Page<EmployeeDto>> allUser = employeeController.getAllUser(0);

		assertEquals(HttpStatus.OK, allUser.getStatusCode());
		assertEquals(page, allUser.getBody());
	}

	@Test
	void testFindUser() throws UserEmailNotFoundException {
		
		when(userService.findUserByEmail(employeeDto.getEmail())).thenReturn(List.of(employeeDto));
		ResponseEntity<List<EmployeeDto>> findUser = employeeController.findUser(employeeDto.getEmail());
		
		assertEquals(HttpStatus.OK, findUser.getStatusCode());
		assertEquals(List.of(employeeDto), findUser.getBody());
	}

}

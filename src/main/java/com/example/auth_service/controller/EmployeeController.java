package com.example.auth_service.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.auth_service.custom_exceptions.UserEmailNotFoundException;
import com.example.auth_service.dto.EmployeeDto;
import com.example.auth_service.services.UserService;

@RestController
@RequestMapping("employee")
public class EmployeeController {

	@Autowired
	UserService userService;
	
	@GetMapping("/getAll")
	public ResponseEntity<Page<EmployeeDto>> getAllUser(@RequestParam int pageNo){
		return new ResponseEntity<>(userService.getPaginatedResults(pageNo), HttpStatus.OK);
	}
	
	@GetMapping("/findUser/{email}")
	public ResponseEntity<List<EmployeeDto>> findUser(@PathVariable String email) throws UserEmailNotFoundException{
		return new ResponseEntity<>(userService.findUserByEmail(email),HttpStatus.OK);
	}
}
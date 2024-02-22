 package com.example.auth_service.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Document
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
	
	@Id
	private String id;
	
	@Indexed(unique = true)
	private String email;
	private String password;
	private String role;

	public User(String email, String password, String role) {
		super();
		this.email = email;
		this.password = password;
		this.role = role;
	}
}

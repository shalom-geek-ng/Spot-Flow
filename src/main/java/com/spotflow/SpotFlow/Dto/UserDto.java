package com.spotflow.SpotFlow.Dto;

import lombok.Data;

@Data
public class UserDto {
	private String email;
	private String password;
	public UserDto(String email) {
		super();
		this.email = email;
	}
	public UserDto(String email, String password) {
		super();
		this.email = email;
		this.password = password;
	}

	
	
}

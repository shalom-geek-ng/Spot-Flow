package com.spotflow.SpotFlow.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class UserDto {
	private String email;
	private String password;
	public UserDto(String email) {
		super();
		this.email = email;
	}
	
	
}

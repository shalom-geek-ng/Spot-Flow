package com.spotflow.SpotFlow.Dto;

import lombok.Data;

@Data
public class AuthRequest {
	private String email;
	private String password;
}


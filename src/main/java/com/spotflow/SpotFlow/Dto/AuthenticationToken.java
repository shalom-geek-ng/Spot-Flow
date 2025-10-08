package com.spotflow.SpotFlow.Dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AuthenticationToken {
	private String token;
}

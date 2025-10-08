package com.spotflow.SpotFlow.Dto;

import java.time.LocalDateTime;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UrlCreatorDto {
	private String originalUrl;
	private LocalDateTime createdAt;
	private LocalDateTime expiresAt;
	private String shortCode;
	private long clickCount;
	public UrlCreatorDto(String originalUrl, LocalDateTime createdAt, LocalDateTime expiresAt, String shortCode) {
		super();
		this.originalUrl = originalUrl;
		this.createdAt = createdAt;
		this.expiresAt = expiresAt;
		this.shortCode = shortCode;
	}
	
}

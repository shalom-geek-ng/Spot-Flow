package com.spotflow.SpotFlow.Dto;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class UrlDto {
	private String originalUrl;
	private LocalDateTime createdAt;
	private LocalDateTime expiresAt;
	private long clickCount;
}

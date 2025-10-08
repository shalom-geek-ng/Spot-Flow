package com.spotflow.SpotFlow.Dto;

import java.time.LocalDateTime;
import java.util.List;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UrlAnalyticsDto {
	private Long userId;
	private List<UrlCreated> urls;
	
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
	public static class UrlCreated{
		private String originalUrl;
		private String shortUrl;
		private long count;
		private LocalDateTime createdAt;
		private LocalDateTime expiresAt;
	
	}
	
	
}

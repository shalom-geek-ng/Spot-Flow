package com.spotflow.SpotFlow.Services;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.spotflow.SpotFlow.Dto.UrlDto;
import com.spotflow.SpotFlow.Entities.Url;
import com.spotflow.SpotFlow.Entities.User;
import com.spotflow.SpotFlow.Repository.UrlRepository;
import com.spotflow.SpotFlow.Repository.UserRepo;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class UrlCreationService {
	private final UserRepo userRepo;
	private final UrlRepository urlRepo;
	
	private User getLoggedInUser() {
		 Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		 return (principal instanceof User) ? (User) principal : null;
	}
	
	public UrlDto createUrl(UrlDto dto) {
		
		if(dto.getOriginalUrl() == null || !dto.getOriginalUrl().startsWith("http")) {
			throw new IllegalArgumentException("Invalid URL format");
		}
		
		 String shortCode = UUID.randomUUID().toString().substring(0, 6);
		 
		 LocalDateTime createdAt = LocalDateTime.now();
		 LocalDateTime expiresAt = createdAt.plusDays(1);
		 
		 Url url = new Url();
		 url.setOriginalUrl(dto.getOriginalUrl());
		 url.setShortCode(shortCode);
		 url.setCreatedAt(createdAt);
		 url.setExpiresAt(expiresAt);
		 
		 User loggedInUser = getLoggedInUser();
		 if(loggedInUser!=null) {
		 url.setCreatedBy(loggedInUser);
		 }
		 
		 url.setClickCount(0);
	
		  Url savedUrl = urlRepo.save(url);
		 
		  	UrlDto response = new UrlDto();
	        response.setOriginalUrl(savedUrl.getOriginalUrl());
	        response.setShortCode(savedUrl.getShortCode());
	        response.setCreatedAt(savedUrl.getCreatedAt());
	        response.setExpiresAt(savedUrl.getExpiresAt());
	        response.setClickCount(savedUrl.getClickCount());
		 
		 return response;
	}
}

package com.spotflow.SpotFlow.Services;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.spotflow.SpotFlow.Dto.UrlAnalyticsDto;
import com.spotflow.SpotFlow.Dto.UrlCreatorDto;
import com.spotflow.SpotFlow.Dto.UrlDto;
import com.spotflow.SpotFlow.Dto.UserDto;
import com.spotflow.SpotFlow.Entities.Url;
import com.spotflow.SpotFlow.Entities.User;
import com.spotflow.SpotFlow.ExceptionHandler.ResponseNotFoundException;
import com.spotflow.SpotFlow.Repository.UrlRepository;
import com.spotflow.SpotFlow.Repository.UserRepo;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class UrlService {
	private final UserRepo userRepo;
	private final UrlRepository urlRepo;
	
	private User getLoggedInUser() {
		 Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		 return (principal instanceof User) ? (User) principal : null;
	}
//	1. create url so that shortened url can be generated
	public UrlCreatorDto createUrl(UrlCreatorDto dto) {
		
		if(dto.getOriginalUrl() == null || !dto.getOriginalUrl().startsWith("http")) {
			throw new ResponseNotFoundException("Invalid URL format");
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
		 
		  UrlCreatorDto response = new UrlCreatorDto();
	        response.setOriginalUrl(savedUrl.getOriginalUrl());
	        response.setShortCode(savedUrl.getShortCode());
	        response.setCreatedAt(savedUrl.getCreatedAt());
	        response.setExpiresAt(savedUrl.getExpiresAt());
	        response.setClickCount(savedUrl.getClickCount());
		 
		 return response;
	}
	
//	2. get original url by short version
	public UrlDto getOriginalUrlByShortenedVersion(String shortenedUrl) {
		Url url = urlRepo.findByShortCode(shortenedUrl).orElseThrow();
		LocalDateTime now = LocalDateTime.now();
		
		if(url.getExpiresAt().isBefore(now)) {
			throw new ResponseNotFoundException("This short URL has expired");
		}
		url.setClickCount(url.getClickCount()+1);
		urlRepo.save(url);
		
		return new UrlDto(url.getOriginalUrl(), url.getCreatedAt(), url.getExpiresAt());
		
	}
	
//	3. Get analytics by authenticated users
	
	public UrlAnalyticsDto getUrlAnaltyicsByAuthenticatedUser() {
		User loggedInUser = getLoggedInUser();
		 if(loggedInUser==null) {
		 throw new ResponseNotFoundException("It's only authenticated users that have this priviledge, pls consider signing up");
		 }
		 List<Url> urls = urlRepo.findByCreatedBy(loggedInUser);
		 
		 if (urls.isEmpty()) {
		        throw new ResponseNotFoundException("You haven't created any shortened URLs yet.");
		    }
		 
		 List<UrlAnalyticsDto.UrlCreated> analyticsList = urls.stream()
				 .map(url -> new UrlAnalyticsDto.UrlCreated(
						 url.getOriginalUrl(),
		                    url.getShortCode(),
		                    url.getClickCount(),
		                    url.getCreatedAt(),
		                    url.getExpiresAt()
						 )).toList();
		 
		 UrlAnalyticsDto analyticsDto = new UrlAnalyticsDto();
		 analyticsDto.setUserId(
		                    loggedInUser.getId());   
		 analyticsDto.setUrls(analyticsList);

		    return analyticsDto;
	}
	
//	4. Create personal short code by authenticated user
	public UrlCreatorDto createPersonalShortCode(UrlCreatorDto dto) {
		User loggedInUser = getLoggedInUser(); 
		if (loggedInUser == null) {
		        throw new ResponseNotFoundException("Only authenticated users can create personalized short links. Please log in.");
		    }

		    if (dto.getOriginalUrl() == null || !dto.getOriginalUrl().startsWith("http")) {
		        throw new ResponseNotFoundException("Invalid URL format");
		    }

		    if (dto.getShortCode() == null || dto.getShortCode().isEmpty()) {
		        throw new ResponseNotFoundException("You must provide a desired short code");
		    }
		    
		    if(urlRepo.findByShortCode(dto.getShortCode()).isPresent()) {
		    	 throw new ResponseNotFoundException("This short code is already in use. Please choose another one.");
		    }
		    LocalDateTime createdAt = LocalDateTime.now();
		    LocalDateTime expiresAt = createdAt.plusDays(7); // personal URLs last longer, maybe 7 days?

		    Url url = new Url();
		    url.setOriginalUrl(dto.getOriginalUrl());
		    url.setShortCode(dto.getShortCode()); 
		    url.setCreatedAt(createdAt);
		    url.setExpiresAt(expiresAt);
		    url.setCreatedBy(loggedInUser);
		    url.setClickCount(0);

		    Url savedUrl = urlRepo.save(url);

		    
		    UrlCreatorDto response = new UrlCreatorDto();
		    response.setOriginalUrl(savedUrl.getOriginalUrl());
		    response.setShortCode(savedUrl.getShortCode());
		    response.setCreatedAt(savedUrl.getCreatedAt());
		    response.setExpiresAt(savedUrl.getExpiresAt());
		    response.setClickCount(savedUrl.getClickCount());

		    return response;
	}
}

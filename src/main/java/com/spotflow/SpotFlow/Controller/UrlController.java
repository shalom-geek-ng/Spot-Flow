package com.spotflow.SpotFlow.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.spotflow.SpotFlow.Dto.UrlAnalyticsDto;
import com.spotflow.SpotFlow.Dto.UrlCreatorDto;
import com.spotflow.SpotFlow.Services.UrlService;


@RestController
@RequestMapping("/api")
public class UrlController {
    
    @Autowired
    private UrlService urlService;
    
    @PostMapping("/v1/create-url")
    public ResponseEntity<?> createUrl(@RequestBody UrlCreatorDto urlDto){
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(urlService.createUrl(urlDto));
    }
    
    @GetMapping("/v1/get-original-url-by-shortened/{shortUrl}")
    public ResponseEntity<?> getOriginalUrl(@PathVariable String shortUrl){
    	return ResponseEntity.ok(urlService.getOriginalUrlByShortenedVersion(shortUrl));
    }
    
    @GetMapping("/v1/get-url-analytics")
    public ResponseEntity<?> getUrlAnalytics(){
    	return ResponseEntity.ok(urlService.getUrlAnaltyicsByAuthenticatedUser());
    }
    
    @PostMapping("/v1/create-personal-short-url")
    public ResponseEntity<?> createPersonalShortUrl( @RequestBody UrlCreatorDto dto){
    	return ResponseEntity.status(HttpStatus.CREATED)
                .body(urlService.createPersonalShortCode(dto));
    }
}
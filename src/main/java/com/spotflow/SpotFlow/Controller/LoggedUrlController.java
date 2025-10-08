package com.spotflow.SpotFlow.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.spotflow.SpotFlow.Dto.UrlDto;
import com.spotflow.SpotFlow.Services.UrlCreationService;


@RestController
@RequestMapping("/api")
public class LoggedUrlController {
    
    @Autowired
    private UrlCreationService urlService;
    
    @GetMapping("/hello")
    public ResponseEntity<String> hello() {
        return ResponseEntity.ok("hello");
    }
    
    @PostMapping("/v1/create-url")
    public ResponseEntity<?> createUrl(@RequestBody UrlDto urlDto){
        return ResponseEntity.ok(urlService.createUrl(urlDto));
    }
}
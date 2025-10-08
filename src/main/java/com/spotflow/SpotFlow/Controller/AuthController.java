package com.spotflow.SpotFlow.Controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.spotflow.SpotFlow.Dto.AuthRequest;
import com.spotflow.SpotFlow.Dto.AuthenticationToken;
import com.spotflow.SpotFlow.Services.AuthService;

import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping("/api/auth")
public class AuthController {
	private final AuthService authService;
	@PostMapping("/user/login")
//	AuthenticationToken
	public ResponseEntity<Object> userlogin(@RequestBody AuthRequest request, HttpServletResponse response){
		AuthenticationToken token = authService.login(request,response);
	
		return ResponseEntity.ok(token);
	}
}

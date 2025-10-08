package com.spotflow.SpotFlow.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.spotflow.SpotFlow.Dto.UserDto;
import com.spotflow.SpotFlow.Services.UserService;

@RestController
@RequestMapping("/api")
public class UserController {
	@Autowired
	private UserService userService;
	@PostMapping("/v1/create-user")
	public ResponseEntity<?> createUser(@RequestBody UserDto dto){
		return ResponseEntity.status(HttpStatus.CREATED).body(userService.createNewUser(dto));
	}
}

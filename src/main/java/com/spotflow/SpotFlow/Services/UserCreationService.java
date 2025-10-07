package com.spotflow.SpotFlow.Services;

import org.springframework.stereotype.Service;

import com.spotflow.SpotFlow.Dto.UserDto;
import com.spotflow.SpotFlow.Entities.User;
import com.spotflow.SpotFlow.Repository.UserRepo;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class UserCreationService {
	private final UserRepo userRepo;
	
//	public UserDto createNewUser(UserDto dto) {
//		User user = userRepo.findbyEmail(dto.getEmail()).orElse();
//	}
}

package com.spotflow.SpotFlow.Services;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.spotflow.SpotFlow.Dto.UserDto;
import com.spotflow.SpotFlow.Entities.User;
import com.spotflow.SpotFlow.ExceptionHandler.ResponseNotFoundException;
import com.spotflow.SpotFlow.Repository.UserRepo;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class UserService {
	private final UserRepo userRepo;
	private final PasswordEncoder passwordEncoder;
	
	public UserDto createNewUser(UserDto dto) {
		
		if(userRepo.findByEmail(dto.getEmail()).isPresent()) {
			throw new ResponseNotFoundException("User with " + dto.getEmail() + " already exists");
		}
		User user = new User();
		user.setEmail(dto.getEmail());
		user.setPassword(passwordEncoder.encode(dto.getPassword()));
		User savedUser = userRepo.save(user);
		dto.setEmail(savedUser.getEmail());
			return new UserDto(dto.getEmail());
		}
}

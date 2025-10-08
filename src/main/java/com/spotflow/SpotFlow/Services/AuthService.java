package com.spotflow.SpotFlow.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import com.spotflow.SpotFlow.Dto.AuthRequest;
import com.spotflow.SpotFlow.Dto.AuthenticationToken;
import com.spotflow.SpotFlow.Entities.User;
import com.spotflow.SpotFlow.ExceptionHandler.ResponseNotFoundException;
import com.spotflow.SpotFlow.Repository.UserRepo;
import com.spotflow.SpotFlow.jwt.JwtService;

import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class AuthService {
	
	private final AuthenticationManager authenticateManager;
	private final JwtService jwtService;
	private final UserRepo userRepo;
	
	
	public AuthenticationToken login(AuthRequest request,HttpServletResponse response) {
		try {
			authenticateManager.authenticate(
					new UsernamePasswordAuthenticationToken(request.getEmail(),
							request.getPassword())
					
					);
			
			User useremail = userRepo.findByEmail(request.getEmail()).orElseThrow(() -> new ResponseNotFoundException("Pls re-check your username"));
				
			String token = jwtService.generateToken(useremail);
//			jwtAuthFilter.setJwtCookie(response, token);

			return AuthenticationToken.builder().token(token).build();
		 } 
		catch (BadCredentialsException e) {
		        throw new ResponseNotFoundException("Invalid username or password.");
		    } 
		catch(DisabledException e) {
			throw new ResponseNotFoundException("This account is disabled. Please contact support for assistance.");
		}
		catch (InternalAuthenticationServiceException e) {
		        throw new ResponseNotFoundException("Internal server error. Please try again later.");
		    } 
		catch (Exception e) {
		        // Catch all other exceptions to provide more information
		        throw new ResponseNotFoundException("An unexpected error occurred try again later" );
		    }
		}

}

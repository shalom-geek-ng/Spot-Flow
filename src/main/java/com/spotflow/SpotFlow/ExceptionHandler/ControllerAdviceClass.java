package com.spotflow.SpotFlow.ExceptionHandler;

import java.time.LocalDate;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ControllerAdviceClass extends ResponseEntityExceptionHandler{
	@ExceptionHandler(ResponseNotFoundException.class)
	private ResponseEntity<Response> handleExeption (ResponseNotFoundException ex, WebRequest req){
		Response response = new Response(HttpStatus.NOT_FOUND,req.getDescription(false),
				ex.getMessage(),LocalDate.now());
		
		return new ResponseEntity<Response>(response,HttpStatus.NOT_FOUND);
	}
}
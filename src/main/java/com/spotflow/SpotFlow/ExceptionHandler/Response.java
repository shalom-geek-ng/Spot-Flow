package com.spotflow.SpotFlow.ExceptionHandler;

import java.time.LocalDate;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Response {
	private int status;
	private String description;
	private String message;
	private LocalDate localDate;
	
    
    public Response(HttpStatus status, String description, String message, LocalDate localDate) {
        this.status = status.value(); 
        this.description = description;
        this.message = message;
        this.localDate = localDate;
    }
}


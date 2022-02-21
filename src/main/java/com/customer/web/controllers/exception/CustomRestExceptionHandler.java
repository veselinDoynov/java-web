package com.customer.web.controllers.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class CustomRestExceptionHandler {

	@ExceptionHandler
	public ResponseEntity<CustomErrorResponse> handleException(CustomNotFoundException exc) {
		
		CustomErrorResponse error = new CustomErrorResponse();
		
		error.setStatus(HttpStatus.NOT_FOUND.value());
		error.setMessage(exc.getMessage());
		error.setTimeStamp(new java.util.Date());
		
		return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler
	public ResponseEntity<CustomErrorResponse> handleException(Exception exc) {
		
		CustomErrorResponse error = new CustomErrorResponse();
		
		error.setStatus(HttpStatus.BAD_REQUEST.value());
		error.setMessage(exc.getMessage());
		error.setTimeStamp(new java.util.Date());

		return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
	}
}

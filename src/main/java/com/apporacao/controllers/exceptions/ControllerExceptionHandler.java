package com.apporacao.controllers.exceptions;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.apporacao.exceptions.ObjectNotFoundException;
import com.apporacao.exceptions.TimeExpirationException;
import com.apporacao.exceptions.UsernameNotFoundException;

@ControllerAdvice
public class ControllerExceptionHandler {

	private StandardError standardError = null;
	
	@ExceptionHandler(UsernameNotFoundException.class)
	public ResponseEntity<StandardError> usernameNotFound(UsernameNotFoundException e, HttpServletRequest request){
		standardError = new StandardError(
				new Date(System.currentTimeMillis()), 
				HttpStatus.NOT_FOUND.value(), 
				"Não encontrado.", 
				e.getMessage(), 
				request.getRequestURI());
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(standardError);
	}
	
	@ExceptionHandler(ObjectNotFoundException.class)
	public ResponseEntity<StandardError> objectNotFound(ObjectNotFoundException e, HttpServletRequest request){
		standardError = new StandardError(
				new Date(System.currentTimeMillis()), 
				HttpStatus.NOT_FOUND.value(), 
				"Não encontrado.", 
				e.getMessage(), 
				request.getRequestURI());
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(standardError);
	}
	
	@ExceptionHandler(TimeExpirationException.class)
	public ResponseEntity<StandardError> timeExpirated(TimeExpirationException e, HttpServletRequest request){
		standardError = new StandardError(
				new Date(System.currentTimeMillis()), 
				HttpStatus.FORBIDDEN.value(), 
				"Proibido.", 
				e.getMessage(), 
				request.getRequestURI());
		return ResponseEntity.status(HttpStatus.FORBIDDEN).body(standardError);
	}
	
	
	
}

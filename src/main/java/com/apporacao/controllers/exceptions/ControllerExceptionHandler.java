package com.apporacao.controllers.exceptions;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.apporacao.exceptions.AuthorizationException;
import com.apporacao.exceptions.EmailReceiverNotEqualException;
import com.apporacao.exceptions.EmailSenderNotEqualException;
import com.apporacao.exceptions.ObjectNotFoundException;
import com.apporacao.exceptions.SQLViolationException;
import com.apporacao.exceptions.TimeExpirationException;
import com.apporacao.exceptions.UserEqualsPedidoUserException;
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
	
	@ExceptionHandler(EmailReceiverNotEqualException.class)
	public ResponseEntity<StandardError> emailReceiverNotEqualed(EmailReceiverNotEqualException e, HttpServletRequest request){
		standardError = new StandardError(
				new Date(System.currentTimeMillis()), 
				HttpStatus.BAD_REQUEST.value(), 
				"Requisição inválida.", 
				e.getMessage(), 
				request.getRequestURI());
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(standardError);
	}
	
	@ExceptionHandler(AuthorizationException.class)
	public ResponseEntity<StandardError> notAuthorized(AuthorizationException e, HttpServletRequest request){
		standardError = new StandardError(
				new Date(System.currentTimeMillis()), 
				HttpStatus.UNAUTHORIZED.value(), 
				"Não autorizado.", 
				e.getMessage(), 
				request.getRequestURI());
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(standardError);
	}
	
	@ExceptionHandler(SQLViolationException.class)
	public ResponseEntity<StandardError> duplicateEmail(SQLViolationException e, HttpServletRequest request){
		standardError = new StandardError(
				new Date(System.currentTimeMillis()), 
				HttpStatus.BAD_REQUEST.value(), 
				"Erro de requisição.", 
				e.getMessage(), 
				request.getRequestURI());
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(standardError);
	}
	
	@ExceptionHandler(UserEqualsPedidoUserException.class)
	public ResponseEntity<StandardError> userEqualsPedidoUser(UserEqualsPedidoUserException e, HttpServletRequest request){
		standardError = new StandardError(
				new Date(System.currentTimeMillis()), 
				HttpStatus.BAD_REQUEST.value(), 
				"Erro de requisição.", 
				e.getMessage(), 
				request.getRequestURI());
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(standardError);
	}
	
	@ExceptionHandler(EmailSenderNotEqualException.class)
	public ResponseEntity<StandardError> emailSenderNotEqualed(EmailSenderNotEqualException e, HttpServletRequest request){
		standardError = new StandardError(
				new Date(System.currentTimeMillis()), 
				HttpStatus.BAD_REQUEST.value(), 
				"Erro de requisição.", 
				e.getMessage(), 
				request.getRequestURI());
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(standardError);
	}
	
	
	
	
}

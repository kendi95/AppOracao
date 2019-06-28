package com.apporacao.exceptions;

public class UsernameNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	public UsernameNotFoundException(String message) {
		super(message);
	}
	
	public UsernameNotFoundException(String message, Throwable cause) {
		super(message, cause);
	}

}

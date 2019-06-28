package com.apporacao.exceptions;

public class TimeExpirationException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	public TimeExpirationException(String message) {
		super(message);
	}
	
	public TimeExpirationException(String message, Throwable cause) {
		super(message, cause);
	}

}

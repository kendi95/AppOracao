package com.apporacao.exceptions;

public class SQLViolationException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	public SQLViolationException(String message) {
		super(message);
	}
	
	public SQLViolationException(String message, Throwable cause) {
		super(message, cause);
	}

}

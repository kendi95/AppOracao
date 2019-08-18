package com.apporacao.exceptions;

public class EmailSenderNotEqualException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	public EmailSenderNotEqualException(String message) {
		super(message);
	}
	
	public EmailSenderNotEqualException(String message, Throwable cause) {
		super(message, cause);
	}

}

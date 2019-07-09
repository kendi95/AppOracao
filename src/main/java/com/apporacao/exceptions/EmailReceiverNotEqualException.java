package com.apporacao.exceptions;

public class EmailReceiverNotEqualException extends RuntimeException{

	private static final long serialVersionUID = 1L;
	
	public EmailReceiverNotEqualException(String message) {
		super(message);
	}
	
	public EmailReceiverNotEqualException(String message, Throwable cause) {
		super(message, cause);
	}

}

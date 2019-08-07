package com.apporacao.exceptions;

public class UserEqualsPedidoUserException extends RuntimeException{

	private static final long serialVersionUID = 1L;
	
	public UserEqualsPedidoUserException(String message) {
		super(message);
	}
	
	public UserEqualsPedidoUserException(String message, Throwable cause) {
		super(message, cause);
	}

}

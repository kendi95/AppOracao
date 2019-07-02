package com.apporacao.dtos;

import java.io.Serializable;

public class AuthenticationResponse implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String tipo;
	
	public AuthenticationResponse() {}
	
	public AuthenticationResponse(String tipo) {
		this.tipo = tipo;
	}
	
	public String getTipo() {
		return tipo;
	}

}

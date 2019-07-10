package com.apporacao.dtos;

import java.io.Serializable;

public class SenhaDTO implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String senha;
	private String codeSecurity;
	
	public SenhaDTO() {}
	
	public SenhaDTO(String senha, String codeSecurity) {
		this.senha = senha;
		this.codeSecurity = codeSecurity;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}
	
	public String getCodeSecurity() {
		return codeSecurity;
	}
	
	public void setCodeSecurity(String codeSecurity) {
		this.codeSecurity = codeSecurity;
	}

}

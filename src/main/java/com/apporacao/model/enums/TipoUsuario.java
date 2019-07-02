package com.apporacao.model.enums;

public enum TipoUsuario {

	COMUM(0, "ROLE_COMUM"),
	ADMIN(1, "ROLE_ADMIN");
	
	private int id;
	private String tipo;
	
	private TipoUsuario(int id, String tipo) {
		this.id = id;
		this.tipo = tipo;
	}
	
	public int getId() {
		return id;
	}
	
	public String getTipo() {
		return tipo;
	}
	
}

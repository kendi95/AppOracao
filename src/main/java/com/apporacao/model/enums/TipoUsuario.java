package com.apporacao.model.enums;

public enum TipoUsuario {

	COMUM(1, "Comum"),
	ADMIN(2, "Administrador");
	
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

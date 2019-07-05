package com.apporacao.dtos;

import java.io.Serializable;

public class TipoDTO implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private int id;
	private String tipo;
	
	public TipoDTO() {}
	
	public TipoDTO(String tipo) {
		this.tipo = tipo;
	}
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public String getTipo() {
		return tipo;
	}
	
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

}

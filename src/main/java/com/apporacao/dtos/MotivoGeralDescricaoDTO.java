package com.apporacao.dtos;

import java.io.Serializable;

import com.apporacao.model.MotivoGeralDescricao;

public class MotivoGeralDescricaoDTO implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Long id;
	
	private String descricao;

	
	public MotivoGeralDescricaoDTO() {}
	
	public MotivoGeralDescricaoDTO(MotivoGeralDescricao descricao) {
		this.id = descricao.getId();
		this.descricao = descricao.getDescricao();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	

}

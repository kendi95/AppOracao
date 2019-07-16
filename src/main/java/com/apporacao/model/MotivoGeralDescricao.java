package com.apporacao.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class MotivoGeralDescricao implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String descricao;
	
	@JsonIgnore
	@OneToOne
	private MotivoGeral motivoGeral;
	
	
	public MotivoGeralDescricao(Long id, String descricao, MotivoGeral motivoGeral) {
		super();
		this.id = id;
		this.descricao = descricao;
		this.motivoGeral = motivoGeral;
	}
	
	public MotivoGeralDescricao() {}

	
	
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
	
	public MotivoGeral getMotivoGeral() {
		return motivoGeral;
	}
	
	public void setMotivoGeral(MotivoGeral motivoGeral) {
		this.motivoGeral = motivoGeral;
	}

	
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		MotivoGeralDescricao other = (MotivoGeralDescricao) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
	
	

}

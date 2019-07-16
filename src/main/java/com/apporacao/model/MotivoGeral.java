package com.apporacao.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class MotivoGeral implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String descricao;
	
	@JsonIgnore
	@OneToMany(mappedBy = "motivoGeral", cascade = CascadeType.ALL)
	private List<MotivoGeralDescricao> mGDescricao = new ArrayList<>();
	
	public MotivoGeral() {}

	public MotivoGeral(Long id, String descricao) {
		super();
		this.id = id;
		this.descricao = descricao;
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

	public List<MotivoGeralDescricao> getmGDescricao() {
		return mGDescricao;
	}
	
	public void setmGDescricao(List<MotivoGeralDescricao> mGDescricao) {
		this.mGDescricao = mGDescricao;
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
		MotivoGeral other = (MotivoGeral) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
}

package com.apporacao.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;


import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
public class PedidoOracao implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@OneToOne
	private SuperUsuario superUsuario;
	@OneToOne
	private Usuario usuario;
	
	private String motivoGeral;
	private String motivoPessoal;
	private String motivoDescricao;
	
	private String isAnonimo;
	
	@JsonFormat(pattern = "dd/MM/yyyy")
	private Date data_pedido;
	
	
	public PedidoOracao() {}
	
	public PedidoOracao(Long id, SuperUsuario superUsuario, Usuario usuario, 
			String motivoGeral, String motivoPessoal, String motivoDescricao, String isAnonimo, Date data_pedido) {
		this.id = id;
		this.superUsuario = superUsuario;
		this.usuario = usuario;
		this.motivoGeral = motivoGeral;
		this.motivoPessoal = motivoPessoal;
		this.motivoDescricao = motivoDescricao;
		this.isAnonimo = isAnonimo;
		this.data_pedido = data_pedido;
	}

	
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public SuperUsuario getSuperUsuario() {
		return superUsuario;
	}

	public void setSuperUsuario(SuperUsuario superUsuario) {
		this.superUsuario = superUsuario;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	
	public Date getData_pedido() {
		return data_pedido;
	}
	
	public void setData_pedido(Date data_pedido) {
		this.data_pedido = data_pedido;
	}
	
	public String getMotivoDescricao() {
		return motivoDescricao;
	}
	
	public void setMotivoDescricao(String motivoDescricao) {
		this.motivoDescricao = motivoDescricao;
	}
	
	public String getMotivoGeral() {
		return motivoGeral;
	}
	
	public void setMotivoGeral(String motivoGeral) {
		this.motivoGeral = motivoGeral;
	}
	
	public String getMotivoPessoal() {
		return motivoPessoal;
	}
	
	public void setMotivoPessoal(String motivoPessoal) {
		this.motivoPessoal = motivoPessoal;
	}
	
	public String getIsAnonimo() {
		return isAnonimo;
	}
	
	public void setIsAnonimo(String isAnonimo) {
		this.isAnonimo = isAnonimo;
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
		PedidoOracao other = (PedidoOracao) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}	

}

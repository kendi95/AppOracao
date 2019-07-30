package com.apporacao.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class PedidoOracao implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ManyToMany
	@JoinTable(name = "PEDIDO_ORACAO_USUARIO",
		joinColumns = @JoinColumn(name = "pedido_oracao_id"),
		inverseJoinColumns = @JoinColumn(name = "usuario_id"))
	private List<Usuario> usuarios = new ArrayList<>(); 
	
	@ManyToMany
	@JoinTable(name = "PEDIDO_ORACAO_SUPER_USUARIO",
		joinColumns = @JoinColumn(name = "pedido_oracao_id"),
		inverseJoinColumns = @JoinColumn(name = "superUsuario_id"))
	private List<SuperUsuario> superUsuarios = new ArrayList<>(); 
	
	@JsonIgnore
	@OneToOne
	private Usuario usuario;
	
	@JsonIgnore
	@OneToOne
	private SuperUsuario superUsuario;
	
	private String motivoGeral;
	private String motivoPessoal;
	private String motivoDescricao;
	
	private String isAnonimo;
	
	@JsonFormat(pattern = "dd/MM/yyyy")
	private Date data_pedido;
	
	
	public PedidoOracao() {}
	
	public PedidoOracao(Long id, Usuario usuario, SuperUsuario superUsuario, String motivoGeral, String motivoPessoal, String motivoDescricao, 
			String isAnonimo, Date data_pedido) {
		this.id = id;
		this.usuario = usuario;
		this.superUsuario = superUsuario;
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
	
	public List<Usuario> getUsuarios() {
		return usuarios;
	}
	
	public void setUsuarios(List<Usuario> usuarios) {
		this.usuarios = usuarios;
	}
	
	public List<SuperUsuario> getSuperUsuarios() {
		return superUsuarios;
	}
	
	public void setSuperUsuarios(List<SuperUsuario> superUsuarios) {
		this.superUsuarios = superUsuarios;
	}
	
	public SuperUsuario getSuperUsuario() {
		return superUsuario;
	}
	
	public void setSuperUsuario(SuperUsuario superUsuario) {
		this.superUsuario = superUsuario;
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

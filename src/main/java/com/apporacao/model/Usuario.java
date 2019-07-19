package com.apporacao.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import com.apporacao.model.enums.TipoUsuario;
import com.fasterxml.jackson.annotation.JsonIgnore;


@Entity
public class Usuario implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String nome;
	private String email;
	@JsonIgnore
	private String senha;
	
	private String estado;
	private String cidade;
	
	private String telefone;
	
	@JsonIgnore
	@OneToOne
	private SuperUsuario superUsuario;
	
	private TipoUsuario tipo;
	
	@JsonIgnore
	@ManyToMany(mappedBy = "usuario")
	private List<PedidoOracao> pedidos = new ArrayList<>();
	
	
	
	public Usuario() {
		super();
	}


	public Usuario(Long id, String nome, String email, String senha, String estado, String cidade, String telefone, SuperUsuario superusuario) {
		super();
		this.id = id;
		this.nome = nome;
		this.email = email;
		this.senha = senha;
		this.estado = estado;
		this.cidade = cidade;
		this.telefone = telefone;
		this.superUsuario = superusuario;
	}


	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public String getNome() {
		return nome;
	}


	public void setNome(String nome) {
		this.nome = nome;
	}


	public String getEmail() {
		return email;
	}


	public void setEmail(String email) {
		this.email = email;
	}


	public String getSenha() {
		return senha;
	}


	public void setSenha(String senha) {
		this.senha = senha;
	}


	public String getEstado() {
		return estado;
	}


	public void setEstado(String estado) {
		this.estado = estado;
	}


	public String getCidade() {
		return cidade;
	}


	public void setCidade(String cidade) {
		this.cidade = cidade;
	}


	public String getTelefone() {
		return telefone;
	}


	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	public SuperUsuario getSuperUsuario() {
		return superUsuario;
	}
	
	public void setSuperUsuario(SuperUsuario superUsuario) {
		this.superUsuario = superUsuario;
	}
	
	public TipoUsuario getTipo() {
		return tipo;
	}
	
	public void setTipo(TipoUsuario tipo) {
		this.tipo = tipo;
	}
	
	public List<PedidoOracao> getPedidos() {
		return pedidos;
	}
	
	public void setPedidos(List<PedidoOracao> pedidos) {
		this.pedidos = pedidos;
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
		Usuario other = (Usuario) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
}

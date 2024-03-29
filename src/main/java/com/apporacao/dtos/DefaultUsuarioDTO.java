package com.apporacao.dtos;

import java.io.Serializable;

import com.apporacao.model.Usuario;

public class DefaultUsuarioDTO implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Long id;
	private String nome;
	private String email;
	private String senha;
	
	private String telefone;
	
	private String cidade;
	private String estado;
	
	private String conviteEncrypt;
	
	private String imageURL;
	

	
	public DefaultUsuarioDTO() {
		super();
	}


	public DefaultUsuarioDTO(Long id, String nome, String email, String senha, String estado, String cidade, String telefone, String imageURL) {
		super();
		this.id = id;
		this.nome = nome;
		this.email = email;
		this.senha = senha;
		this.estado = estado;
		this.cidade = cidade;
		this.telefone = telefone;
		this.imageURL = imageURL;
	}
	
	
	public DefaultUsuarioDTO(Usuario usuario) {
		this.id = usuario.getId();
		this.nome = usuario.getNome();
		this.email = usuario.getEmail();
		this.telefone = usuario.getTelefone();
		this.cidade = usuario.getCidade();
		this.estado = usuario.getEstado();
		this.imageURL = usuario.getImageURL();
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
	
	public String getConviteEncrypt() {
		return conviteEncrypt;
	}
	
	public void setConviteEncrypt(String conviteEncrypt) {
		this.conviteEncrypt = conviteEncrypt;
	}
	
	public String getImageURL() {
		return imageURL;
	}
	
	public void setImageURL(String imageURL) {
		this.imageURL = imageURL;
	}
	
}

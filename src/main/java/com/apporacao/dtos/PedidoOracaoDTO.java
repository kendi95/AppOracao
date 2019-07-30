package com.apporacao.dtos;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.apporacao.model.PedidoOracao;
import com.apporacao.model.SuperUsuario;
import com.apporacao.model.Usuario;

public class PedidoOracaoDTO implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Long id;
	
	private String motivoGeral;
	private String motivoPessoal;
	private String motivoDescricao;
	
	private String isAnonimo;
	
	private String nome_autor;
	
	private Date data_pedido;
	
	private List<Usuario> usuarios = new ArrayList<>();
	private List<SuperUsuario> superUsuarios = new ArrayList<>();
	
	public PedidoOracaoDTO() {}
	
	public PedidoOracaoDTO(Long id, String motivoGeral, String motivoPessoal, String motivoDescricao) {
		this.id = id;
		this.motivoGeral = motivoGeral;
		this.motivoPessoal = motivoPessoal;
		this.motivoDescricao = motivoDescricao;
	}
	
	public PedidoOracaoDTO(PedidoOracao pedido) {
		this.id = pedido.getId();
		this.motivoGeral = pedido.getMotivoGeral();
		this.motivoPessoal = pedido.getMotivoPessoal();
		this.motivoDescricao = pedido.getMotivoDescricao();
		this.isAnonimo = pedido.getIsAnonimo();
		this.data_pedido = pedido.getData_pedido();
		this.usuarios = pedido.getUsuarios();
		this.superUsuarios = pedido.getSuperUsuarios();
	}

	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
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

	public String getMotivoDescricao() {
		return motivoDescricao;
	}

	public void setMotivoDescricao(String motivoDescricao) {
		this.motivoDescricao = motivoDescricao;
	}
	
	public String getIsAnonimo() {
		return isAnonimo;
	}
	
	public void setIsAnonimo(String isAnonimo) {
		this.isAnonimo = isAnonimo;
	}
	
	public String getNome_autor() {
		return nome_autor;
	}
	
	public void setNome_autor(String nome_autor) {
		this.nome_autor = nome_autor;
	}
	
	public Date getData_pedido() {
		return data_pedido;
	}
	
	public void setData_pedido(Date data_pedido) {
		this.data_pedido = data_pedido;
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

}

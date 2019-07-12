package com.apporacao.dtos;

import java.io.Serializable;
import java.util.Date;

import com.apporacao.model.PedidoOracao;

public class PedidoOracaoDTO implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Long id;
	
	private String motivoGeral;
	private String motivoPessoal;
	private String motivoDescricao;
	
	private Long superUsuario_id;
	private Long usuario_id;
	
	private String isAnonimo;
	
	private String nome_autor;
	
	private Date data_pedido;
	
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
		this.superUsuario_id = pedido.getSuperUsuario().getId();
		this.usuario_id = pedido.getUsuario().getId();
		this.data_pedido = pedido.getData_pedido();
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
	
	public Long getSuperUsuario_id() {
		return superUsuario_id;
	}
	
	public void setSuperUsuario_id(Long superUsuario_id) {
		this.superUsuario_id = superUsuario_id;
	}
	
	public Long getUsuario_id() {
		return usuario_id;
	}
	
	public void setUsuario_id(Long usuario_id) {
		this.usuario_id = usuario_id;
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
	

}

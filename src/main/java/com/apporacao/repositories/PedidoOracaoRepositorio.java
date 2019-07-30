package com.apporacao.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.apporacao.model.PedidoOracao;
import com.apporacao.model.SuperUsuario;
import com.apporacao.model.Usuario;

public interface PedidoOracaoRepositorio extends JpaRepository<PedidoOracao, Long> {

	List<PedidoOracao> findByUsuario(Usuario usuario);
	List<PedidoOracao> findBySuperUsuario(SuperUsuario superUsuario);
	
}

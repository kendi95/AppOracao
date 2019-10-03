package com.apporacao.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import com.apporacao.model.PedidoOracao;
import com.apporacao.model.Usuario;

public interface PedidoOracaoRepositorio extends JpaRepository<PedidoOracao, Long> {

	List<PedidoOracao> findByUsuario(Usuario usuario);
	
}

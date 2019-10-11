package com.apporacao.repositories;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import com.apporacao.model.PedidoOracao;
import com.apporacao.model.Usuario;

public interface PedidoOracaoRepositorio extends JpaRepository<PedidoOracao, Long> {

	List<PedidoOracao> findByUsuario(Usuario usuario);
	
	@Transactional(readOnly = true)
	@Query(value = "SELECT p FROM PedidoOracao p")
	Page<PedidoOracao> search(Pageable pageRequest);
	
}

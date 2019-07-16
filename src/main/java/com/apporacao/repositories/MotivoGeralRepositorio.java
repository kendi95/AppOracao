package com.apporacao.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.apporacao.model.MotivoGeral;

public interface MotivoGeralRepositorio extends JpaRepository<MotivoGeral, Long> {

	MotivoGeral findByDescricao(String descricao);
	
}

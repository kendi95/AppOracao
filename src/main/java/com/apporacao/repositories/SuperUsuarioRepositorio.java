package com.apporacao.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.apporacao.model.SuperUsuario;

public interface SuperUsuarioRepositorio extends JpaRepository<SuperUsuario, Long> {

	SuperUsuario findByEmail(String email);
	
}

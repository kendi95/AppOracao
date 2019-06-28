package com.apporacao.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.apporacao.model.Usuario;

public interface UsuarioRepositorio extends JpaRepository<Usuario, Long> {

	Usuario findByEmail(String email);
	
}

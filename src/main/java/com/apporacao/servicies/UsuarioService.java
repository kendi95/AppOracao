package com.apporacao.servicies;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apporacao.dtos.UsuarioDTO;
import com.apporacao.model.Usuario;
import com.apporacao.repositories.UsuarioRepositorio;

@Service
public class UsuarioService {

	@Autowired
	private UsuarioRepositorio repo;
	
	public void insert(UsuarioDTO dto) {
		repo.save(new Usuario(null, dto.getNome(), dto.getEmail(), dto.getSenha(), dto.getEstado(), dto.getCidade(), dto.getTelefone()));
	}
	
}

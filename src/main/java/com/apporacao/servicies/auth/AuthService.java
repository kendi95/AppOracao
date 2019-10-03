package com.apporacao.servicies.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.apporacao.dtos.CredencialDTO;
import com.apporacao.exceptions.ObjectNotFoundException;
import com.apporacao.model.Usuario;
import com.apporacao.repositories.UsuarioRepositorio;

@Service
public class AuthService {

	@Autowired
	private UsuarioRepositorio repo;
	@Autowired
	private BCryptPasswordEncoder encoder;
	
	
	public boolean confirmEmail(String email) {
		Usuario usuario = repo.findByEmail(email);
		if(usuario == null) {
			throw new ObjectNotFoundException("Email não encontrado.");
		} else {
			return true;
		}
	}
	
	public void newPassword(CredencialDTO dto) {
		Usuario usuario = repo.findByEmail(dto.getEmail());
		usuario.setSenha(encoder.encode(dto.getSenha()));
		repo.save(usuario);
	}
	
	
}

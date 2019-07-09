package com.apporacao.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import com.apporacao.exceptions.UsernameNotFoundException;
import com.apporacao.model.SuperUsuario;
import com.apporacao.model.Usuario;
import com.apporacao.repositories.SuperUsuarioRepositorio;
import com.apporacao.repositories.UsuarioRepositorio;

@Service
public class UserDetailServiceImplementation implements UserDetailsService {

	@Autowired
	private UsuarioRepositorio repo;
	@Autowired
	private SuperUsuarioRepositorio superUsuarioRe;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Usuario usuario = repo.findByEmail(username);
		if(usuario != null) {
			return new UserDetailImplementation(usuario);
		} else {
			SuperUsuario superUsuario = superUsuarioRe.findByEmail(username);
			if(superUsuario != null) {
				return new UserDetailImplementation(superUsuario);
			}
			throw new UsernameNotFoundException("Nao encontrado.");
			
		}
		
	}
	
	public static UserDetailImplementation getAuthentication() {
		try {
			return (UserDetailImplementation) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		}catch(Exception e) {
			return null;
		}
	}

}

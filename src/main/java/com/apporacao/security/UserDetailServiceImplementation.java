package com.apporacao.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.apporacao.model.Usuario;
import com.apporacao.repositories.UsuarioRepositorio;

@Service
public class UserDetailServiceImplementation implements UserDetailsService {

	@Autowired
	private UsuarioRepositorio repo;

	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Usuario usuario = repo.findByEmail(username);
		if(usuario != null) {
			return new UserDetailImplementation(usuario);
		} else {
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

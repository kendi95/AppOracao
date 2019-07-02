package com.apporacao.security;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.apporacao.model.SuperUsuario;
import com.apporacao.model.Usuario;
import com.apporacao.model.enums.TipoUsuario;

public class UserDetailImplementation implements UserDetails {

	private static final long serialVersionUID = 1L;
	
	private Long id;
	private String username;
	private String password;
	private Collection<? extends GrantedAuthority> authority;
	private List<TipoUsuario> tipos = new ArrayList<>();
	
	
	public UserDetailImplementation(Long id, String username, String password) {
		super();
		this.id = id;
		this.username = username;
		this.password = password;
	}
	
	public UserDetailImplementation(Usuario usuario) {
		tipos.add(usuario.getTipo());
		this.id = usuario.getId();
		this.username = usuario.getEmail();
		this.password = usuario.getSenha();
		this.authority = tipos.stream().map(x -> new SimpleGrantedAuthority(x.getTipo())).collect(Collectors.toList());
		
	}
	
	public UserDetailImplementation(SuperUsuario superUsuario) {
		tipos.add(superUsuario.getTipo());
		this.id = superUsuario.getId();
		this.username = superUsuario.getEmail();
		this.password = superUsuario.getSenha();
		this.authority = tipos.stream().map(x -> new SimpleGrantedAuthority(x.getTipo())).collect(Collectors.toList());
	}
	
	
	
	public Long getId() {
		return id;
	}
	
	

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authority;
	}

	@Override
	public String getPassword() {
		return password;
	}

	@Override
	public String getUsername() {
		return username;
	}

	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return true;
	}

}

package com.apporacao.security;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.apporacao.model.Usuario;

public class UserDetailImplementation implements UserDetails {

	private static final long serialVersionUID = 1L;
	
	private Long id;
	private String username;
	private String password;
	
	
	public UserDetailImplementation(Long id, String username, String password) {
		super();
		this.id = id;
		this.username = username;
		this.password = password;
	}
	
	public UserDetailImplementation(Usuario usuario) {
		this.id = usuario.getId();
		this.username = usuario.getEmail();
		this.password = usuario.getSenha();
	}
	
	public Long getId() {
		return id;
	}
	
	

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return null;
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

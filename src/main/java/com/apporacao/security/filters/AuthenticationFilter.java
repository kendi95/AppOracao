package com.apporacao.security.filters;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.apporacao.dtos.CredencialDTO;
import com.apporacao.security.UserDetailImplementation;
import com.apporacao.security.filters.handlers.JWTAuthenticationFailureHandler;
import com.apporacao.security.utils.JWTUtil;
import com.fasterxml.jackson.databind.ObjectMapper;

public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter{
	
	
	private AuthenticationManager authenticationManager;
	private JWTUtil jwtUtil;
	
	
	public AuthenticationFilter(AuthenticationManager manager, JWTUtil jwtUtil) {
		
		this.authenticationManager = manager;
		this.jwtUtil = jwtUtil;
		setAuthenticationFailureHandler(new JWTAuthenticationFailureHandler());
	}


	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException {
		try {
			CredencialDTO credencial = new ObjectMapper().readValue(request.getInputStream(), CredencialDTO.class);
			UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
					credencial.getEmail(), credencial.getSenha(), new ArrayList<>());
			Authentication authentication = authenticationManager.authenticate(authenticationToken);
			return authentication;
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	
	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
			Authentication authResult) throws IOException, ServletException {
		String email = ((UserDetailImplementation) authResult.getPrincipal()).getUsername();
		String name = ((UserDetailImplementation) authResult.getPrincipal()).getName();
		String token = jwtUtil.createToken(email);
		String tipo = getAuthorities(authResult);
		System.out.println(tipo);
		response.setContentType("application/json");
		response.getOutputStream().print(responseJson(name, tipo));
		response.addHeader("Authorization", "Bearer "+token);
		response.addHeader("access-control-expose-headers", "Authorization");
	}
	
	
	private String getAuthorities(Authentication authResult) {
		String tipo = null;
		for(GrantedAuthority g: ((UserDetailImplementation) authResult.getPrincipal()).getAuthorities()) {
			tipo = g.getAuthority();
		}
		return tipo;
	}
	
	
	private String responseJson(String name, String tipo) {
		return "{\"nome\": \""+name+"\"," +
				"\"tipo\": \""+tipo+"\"}";
	}
	

}

package com.apporacao.security.filters;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.apporacao.dtos.CredencialDTO;
import com.apporacao.security.UserDetailImplementation;
import com.apporacao.security.utils.JWTUtil;
import com.apporacao.securityfilters.handlers.JWTAuthenticationFailureHandler;
import com.fasterxml.jackson.databind.ObjectMapper;

public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter{
	
	private AuthenticationManager authenticationManager;
	private JWTUtil jwtUtil;
	
	
	public AuthenticationFilter(AuthenticationManager manager, JWTUtil jwtUtil) {
		setAuthenticationFailureHandler(new JWTAuthenticationFailureHandler());
		this.authenticationManager = manager;
		this.jwtUtil = jwtUtil;
	}


	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException {
		try {
			CredencialDTO credencial = new ObjectMapper().readValue(request.getInputStream(), CredencialDTO.class);
			UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
					credencial.getEmail(), credencial.getSenha(), null);
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
		String token = jwtUtil.createToken(email);
		response.addHeader("Authorization", "Bearer "+token);
		response.addHeader("access-control-expose-headers", "Authorization");
	}
	

}

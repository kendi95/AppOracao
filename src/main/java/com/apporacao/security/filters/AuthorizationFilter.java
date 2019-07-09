package com.apporacao.security.filters;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import com.apporacao.security.utils.JWTUtil;

public class AuthorizationFilter extends BasicAuthenticationFilter{

	private JWTUtil jwtUtil;
	private UserDetailsService userDetailService;
	
	
	
	public AuthorizationFilter(AuthenticationManager authenticationManager, JWTUtil jwtUtil, UserDetailsService userDetailService) {
		super(authenticationManager);
		this.jwtUtil = jwtUtil;
		this.userDetailService = userDetailService;
	}
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		String headerAuthorization = request.getHeader("Authorization");
		
		if(headerAuthorization != null && headerAuthorization.startsWith("Bearer ")) {
			UsernamePasswordAuthenticationToken authentication = getAuthentication(headerAuthorization.substring(7));
			if(authentication != null) {
				SecurityContextHolder.getContext().setAuthentication(authentication);
			}
		}
		chain.doFilter(request, response);
	}
	
	
	
	private UsernamePasswordAuthenticationToken getAuthentication(String token) {
		if(jwtUtil.isTokenValid(token)) {
			UserDetails user = userDetailService.loadUserByUsername(jwtUtil.getUsername(token));
			return new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
		}
		return null;
	}

}

package com.apporacao.security.filters.handlers;

import java.io.IOException;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

@Component
public class JWTAuthenticationFailureHandler implements AuthenticationFailureHandler {

	
	@Override
	public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException exception) throws IOException, ServletException {
		response.setStatus(HttpStatus.FORBIDDEN.value());
		response.setContentType(MediaType.APPLICATION_JSON_VALUE);
		response.getOutputStream().print(errorAsJson());
	}
	
	private String errorAsJson() {
		long date = new Date(System.currentTimeMillis()).getTime();
		return "{\"timestamp\": "+date+", "+
				"\"status\": "+HttpStatus.FORBIDDEN.value()+", "+
				"\"error\": \"Nao autorizado\", "+
				"\"message\": \"Email ou senha invalida\", "+
				"\"path\": \"/login\"}";
	}

}

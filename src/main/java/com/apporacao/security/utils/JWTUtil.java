package com.apporacao.security.utils;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JWTUtil {
	
	@Value("${jwt.secret}")
	private String secret;
	@Value("${jwt.expiration}")
	private Long expiration;
	
	
	public String createToken(String email){
		String token = Jwts.builder()
				.setSubject(email)
				.setExpiration(new Date(System.currentTimeMillis()+expiration))
				.signWith(SignatureAlgorithm.HS512, secret.getBytes()).compact();
		return token;
	}
	
	public boolean isTokenValid(String token) {
		
		Claims claims = getClaims(token);
		
		if(claims != null) {
			if(claims.getSubject() != null && new Date(System.currentTimeMillis()).before(claims.getExpiration()) 
					&& claims.getExpiration() != null) {
				return true;
			}
		}
		return false;
	}
	
	public String getUsername(String token) {
		Claims claims = getClaims(token);
		if(claims != null){
			return claims.getSubject();
		}
		return null;
	}
	
	
	private Claims getClaims(String token) {
		try {
			return Jwts.parser().setSigningKey(secret.getBytes()).parseClaimsJws(token).getBody();
		}catch(Exception e) {
			return null;
		}
	}

}

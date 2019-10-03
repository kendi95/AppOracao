package com.apporacao.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.apporacao.dtos.CredencialDTO;
import com.apporacao.dtos.SenhaDTO;
import com.apporacao.servicies.auth.AuthService;

@RestController
@RequestMapping("/auth")
public class AuthController {

	@Autowired
	private AuthService service;
	
	@RequestMapping(value = "/verify_email/{email}", method = RequestMethod.GET)
	public ResponseEntity<Void> confirmEmail(@PathVariable("email") String email){
		if(service.confirmEmail(email)) {
			return ResponseEntity.ok().build();
		} else {
			return ResponseEntity.badRequest().build();
		}
	}
	
	@RequestMapping(value = "/new_password", method = RequestMethod.PATCH)
	public ResponseEntity<Void> setNewPassword(@RequestBody CredencialDTO dto){
		service.newPassword(dto);
		return ResponseEntity.ok().build();
	}
	
}

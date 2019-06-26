package com.apporacao.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.apporacao.dtos.UsuarioDTO;
import com.apporacao.servicies.UsuarioService;

@RestController
@RequestMapping(value = "/api")
public class UsuarioController {
	
	@Autowired
	private UsuarioService service;
	
	@RequestMapping(value = "/new_account", method = RequestMethod.POST)
	public ResponseEntity<Void> insert(@RequestBody UsuarioDTO dto){
		service.insert(dto);
		return ResponseEntity.created(null).build();
	}

}

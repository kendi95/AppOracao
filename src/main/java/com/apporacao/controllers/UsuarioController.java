package com.apporacao.controllers;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.apporacao.dtos.ConviteDTO;
import com.apporacao.dtos.DefaultUsuarioDTO;
import com.apporacao.servicies.UsuarioService;

@RestController
@RequestMapping(value = "/api")
public class UsuarioController {
	
	@Autowired
	private UsuarioService service;
	
	@RequestMapping(value = "/new_account", method = RequestMethod.POST)
	public ResponseEntity<Void> insert(@RequestBody DefaultUsuarioDTO dto){
		service.insert(dto);
		return ResponseEntity.created(null).build();
	}
	
	@RequestMapping(value = "/find_all_tipo", method = RequestMethod.GET)
	public ResponseEntity<List<String>> findAllTipo(){
		return ResponseEntity.ok(service.findAllTipo());
	}
	
	@RequestMapping(value = "/create_convite", method = RequestMethod.POST)
	public ResponseEntity<Void> createConvite(@RequestBody ConviteDTO dto){
		service.createConvite(dto);
		return ResponseEntity.created(null).build();
	}
	

}

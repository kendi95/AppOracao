package com.apporacao.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.apporacao.dtos.MotivoGeralDescricaoDTO;
import com.apporacao.model.MotivoGeral;
import com.apporacao.servicies.MotivosService;

@RestController
@RequestMapping("/api")
public class MotivosController {

	@Autowired
	private MotivosService service;
	
	@RequestMapping(value = "/motivos", method = RequestMethod.GET)
	public ResponseEntity<List<MotivoGeral>> findAll(){
		return ResponseEntity.ok(service.findAll());
	}
	
	@RequestMapping(value = "/motivos/{descricao}", method = RequestMethod.GET)
	public ResponseEntity<List<MotivoGeralDescricaoDTO>> findByDescricao(@PathVariable("descricao") String descricao){
		return ResponseEntity.ok(service.findByDescricao(descricao));
	}
	
}

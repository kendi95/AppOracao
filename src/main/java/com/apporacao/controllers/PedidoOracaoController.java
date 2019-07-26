package com.apporacao.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.apporacao.dtos.PedidoOracaoDTO;
import com.apporacao.servicies.PedidoOracaoService;

@RestController
@RequestMapping("/api")
public class PedidoOracaoController {
	
	@Autowired
	private PedidoOracaoService service;

	@RequestMapping(value="/create_pedido", method = RequestMethod.POST)
	public ResponseEntity<Void> createPedido(@RequestBody PedidoOracaoDTO dto){
		service.createPedido(dto);
		return ResponseEntity.created(null).build();
	}
	
	@RequestMapping(value="/pedidos", method = RequestMethod.GET)
	public ResponseEntity<List<PedidoOracaoDTO>> findAll(){
		return ResponseEntity.ok(service.findAll());
	}
	
	@RequestMapping(value="/my_pedidos", method = RequestMethod.GET)
	public ResponseEntity<List<PedidoOracaoDTO>> findPedidosByUsuario(){
		return ResponseEntity.ok(service.findPedidosByUsuario());
	}
	
	@RequestMapping(value="/pedidos/{id}", method = RequestMethod.PATCH)
	public ResponseEntity<Void> insertUsuariosIntoPedido(@PathVariable("id") Long id){
		service.insertUsuariosIntoPedido(id);
		return ResponseEntity.ok().build();
	}
	
	
}

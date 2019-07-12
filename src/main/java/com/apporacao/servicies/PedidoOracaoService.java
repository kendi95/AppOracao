package com.apporacao.servicies;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apporacao.dtos.PedidoOracaoDTO;
import com.apporacao.exceptions.AuthorizationException;
import com.apporacao.exceptions.ObjectNotFoundException;
import com.apporacao.model.PedidoOracao;
import com.apporacao.model.SuperUsuario;
import com.apporacao.model.Usuario;
import com.apporacao.repositories.PedidoOracaoRepositorio;
import com.apporacao.repositories.SuperUsuarioRepositorio;
import com.apporacao.repositories.UsuarioRepositorio;
import com.apporacao.security.UserDetailImplementation;
import com.apporacao.security.UserDetailServiceImplementation;


@Service
public class PedidoOracaoService {

	@Autowired
	private PedidoOracaoRepositorio repo;
	@Autowired
	private UsuarioRepositorio usuarioRepo;
	@Autowired
	private SuperUsuarioRepositorio superUsearioRepo;
	
	private static final Logger LOG = LoggerFactory.getLogger(PedidoOracaoService.class);
	
	public void createPedido(PedidoOracaoDTO dto) {
		UserDetailImplementation user = UserDetailServiceImplementation.getAuthentication();
		if(user == null) {
			throw new AuthorizationException("Email não corresponde com o email de login");
		}
		Usuario usuario = usuarioRepo.findByEmail(user.getUsername());
		if(usuario == null) {
			throw new ObjectNotFoundException("Email não encontrado.");
		}
		dto.setSuperUsuario_id(usuario.getSuperUsuario().getId());
		dto.setUsuario_id(usuario.getId());
		PedidoOracao pedido = new PedidoOracao(null, usuario.getSuperUsuario(), usuario, dto.getMotivoGeral(), 
				dto.getMotivoPessoal(), dto.getMotivoDescricao());
		repo.save(pedido);
		
	}
	
	public List<PedidoOracaoDTO> findAll(){
		UserDetailImplementation user = UserDetailServiceImplementation.getAuthentication();
		if(user == null) {
			throw new AuthorizationException("Email não corresponde com o email de login");
		}
		List<PedidoOracao> pedidos = repo.findAll();
		List<PedidoOracaoDTO> list_dtos = new ArrayList<>();
		Usuario usuario = usuarioRepo.findByEmail(user.getUsername());
		if(usuario == null) {
			SuperUsuario superUsuario = superUsearioRepo.findByEmail(user.getUsername());
			if(superUsuario == null) {
				throw new ObjectNotFoundException("Email não encontrado.");
			}
			for(int i = 0; i < pedidos.size(); i++) {
				if(pedidos.get(i).getSuperUsuario().getEmail().equalsIgnoreCase(superUsuario.getEmail())) {
					PedidoOracaoDTO dto = new PedidoOracaoDTO(pedidos.get(i));
					list_dtos.add(dto);
				}
			}
			return list_dtos;
		}
		
		for(int i = 0; i < pedidos.size(); i++) {
			if(pedidos.get(i).getUsuario().getEmail().equalsIgnoreCase(usuario.getEmail())) {
				PedidoOracaoDTO dto = new PedidoOracaoDTO(pedidos.get(i));
				list_dtos.add(dto);
			}
		}
		return list_dtos;
	}
	
	
	
}

package com.apporacao.servicies;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
	
	
	public void createPedido(PedidoOracaoDTO dto) {
		UserDetailImplementation user = UserDetailServiceImplementation.getAuthentication();
		if(user == null) {
			throw new AuthorizationException("Email não corresponde com o email de login");
		}
		Usuario usuario = usuarioRepo.findByEmail(user.getUsername());
		if(usuario == null) {
			throw new ObjectNotFoundException("Email não encontrado.");
		}
		
		PedidoOracao pedido = null;
		if(dto.getMotivoGeral() != null) {
			pedido = new PedidoOracao(null, usuario, dto.getMotivoGeral(), 
					null, null, dto.getIsAnonimo(), new Date(System.currentTimeMillis()));
		} else {
			pedido = new PedidoOracao(null, usuario, null, 
					dto.getMotivoPessoal(), dto.getMotivoDescricao(), dto.getIsAnonimo(), new Date(System.currentTimeMillis()));
		}
		repo.save(pedido);
	}
	

	public List<PedidoOracaoDTO> findAll(){
		UserDetailImplementation user = UserDetailServiceImplementation.getAuthentication();
		if(user == null) {
			throw new AuthorizationException("Email não corresponde com o email de login");
		} else {
			Usuario usuario = usuarioRepo.findByEmail(user.getUsername());
			if(usuario == null) {
				SuperUsuario superUser = superUsearioRepo.findByEmail(user.getUsername());
				if(superUser == null) {
					throw new ObjectNotFoundException("Email não encontrado.");
				}
			}
			List<PedidoOracaoDTO> dtos = findPedidos();
			return dtos;
		}
	}
	
	
	
	public List<PedidoOracaoDTO> findPedidosByUsuario(){
		UserDetailImplementation user = UserDetailServiceImplementation.getAuthentication();
		if(user == null) {
			throw new AuthorizationException("Email não corresponde com o email de login");
		} else {
			Usuario usuario = usuarioRepo.findByEmail(user.getUsername());
			if(usuario == null) {
				throw new ObjectNotFoundException("Email não encontrado.");
			}
			List<PedidoOracaoDTO> dtos = findPedidosByUsuarios(usuario);
			return dtos;
		}
	}
	
	public void insertUsuariosIntoPedido(Long id) {
		UserDetailImplementation user = UserDetailServiceImplementation.getAuthentication();
		if(user == null) {
			throw new AuthorizationException("Email não corresponde com o email de login");
		} else {
			Usuario usuario = usuarioRepo.findByEmail(user.getUsername());
			PedidoOracao pedido = repo.findById(id).get();
			pedido.getUsuarios().add(usuario);
			repo.save(pedido);
		}
	}
	
	
	
	private List<PedidoOracaoDTO> findPedidos(){
		List<PedidoOracao> pedidos = repo.findAll();
		List<PedidoOracaoDTO> dtos = new ArrayList<>();
		for(PedidoOracao p: pedidos) {
			PedidoOracaoDTO dto = new PedidoOracaoDTO(p);
			if(dto.getIsAnonimo().equalsIgnoreCase("true")) {	
				dto.setNome_autor("Anônimo");
				dto.getUsuarios().addAll(p.getUsuarios());
				dtos.add(dto);
			} else {
				dto.setNome_autor(p.getUsuario().getNome());
				dto.getUsuarios().addAll(p.getUsuarios());
				dtos.add(dto);
			}
			
		}
		return dtos;
	}
	
	private List<PedidoOracaoDTO> findPedidosByUsuarios(Usuario usuario){
		List<PedidoOracao> pedidos = repo.findAll();
		List<PedidoOracaoDTO> dtos = new ArrayList<>();
		for(PedidoOracao p: pedidos) {
			if(p.getUsuario().getId() == usuario.getId()) {
				PedidoOracaoDTO dto = new PedidoOracaoDTO(p);
				if(dto.getIsAnonimo().equalsIgnoreCase("true")) {	
					dto.setNome_autor("Anônimo");
					dtos.add(dto);
				} else {
					dto.setNome_autor(p.getUsuario().getNome());
					dtos.add(dto);
				}
			}
		}
		return dtos;
	}
	
}

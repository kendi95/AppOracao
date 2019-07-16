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
		if(dto.getIsAnonimo().equalsIgnoreCase("true")) {
			if(dto.getMotivoGeral() != null) {
				pedido = new PedidoOracao(null, usuario.getSuperUsuario(), usuario, dto.getMotivoGeral(), 
						null, null, dto.getIsAnonimo(), new Date(System.currentTimeMillis()));
			}
			pedido = new PedidoOracao(null, usuario.getSuperUsuario(), usuario, null, 
					dto.getMotivoPessoal(), dto.getMotivoDescricao(), dto.getIsAnonimo(), new Date(System.currentTimeMillis()));
		} else {
			if(dto.getMotivoGeral() != null) {
				pedido = new PedidoOracao(null, usuario.getSuperUsuario(), usuario, dto.getMotivoGeral(), 
						null, null, dto.getIsAnonimo(), new Date(System.currentTimeMillis()));
			}
			pedido = new PedidoOracao(null, usuario.getSuperUsuario(), usuario, null, 
					dto.getMotivoPessoal(), dto.getMotivoDescricao(), dto.getIsAnonimo(), new Date(System.currentTimeMillis()));
		}
		repo.save(pedido);
	}
	
	public List<PedidoOracaoDTO> findAll(){
		UserDetailImplementation user = UserDetailServiceImplementation.getAuthentication();
		if(user == null) {
			throw new AuthorizationException("Email não corresponde com o email de login");
		} else {
			List<PedidoOracao> pedidos = repo.findAll();
			List<PedidoOracaoDTO> list_dtos = new ArrayList<>();
			Usuario usuario = usuarioRepo.findByEmail(user.getUsername());
			if(usuario == null) {
				SuperUsuario superUsuario = superUsearioRepo.findByEmail(user.getUsername());
				if(superUsuario == null) {
					throw new ObjectNotFoundException("Email não encontrado.");
				} else {
					for(int i = 0; i < pedidos.size(); i++) {
						if(pedidos.get(i).getSuperUsuario().getId() == superUsuario.getId()) {
							if(pedidos.get(i).getIsAnonimo().equalsIgnoreCase("true")) {
								PedidoOracaoDTO dto = new PedidoOracaoDTO(pedidos.get(i));
								dto.setIsAnonimo(pedidos.get(i).getIsAnonimo());
								list_dtos.add(dto);
							} else {
								PedidoOracaoDTO dto = new PedidoOracaoDTO(pedidos.get(i));
								dto.setNome_autor(pedidos.get(i).getUsuario().getNome());
								dto.setIsAnonimo(pedidos.get(i).getIsAnonimo());
								list_dtos.add(dto);
							}
							
						}
					}
					return list_dtos;
				}
				
			} else {
				for(int i = 0; i < pedidos.size(); i++) {
					if(pedidos.get(i).getUsuario().getId() == usuario.getId()) {
						if(pedidos.get(i).getIsAnonimo().equalsIgnoreCase("true")) {
							PedidoOracaoDTO dto = new PedidoOracaoDTO(pedidos.get(i));
							dto.setIsAnonimo(pedidos.get(i).getIsAnonimo());
							list_dtos.add(dto);
						} else {
							PedidoOracaoDTO dto = new PedidoOracaoDTO(pedidos.get(i));
							dto.setNome_autor(pedidos.get(i).getUsuario().getNome());
							dto.setIsAnonimo(pedidos.get(i).getIsAnonimo());
							list_dtos.add(dto);
						}
					}
				}
				return list_dtos;
			}
		}
		
		
		
		
	}
	
	
	
}

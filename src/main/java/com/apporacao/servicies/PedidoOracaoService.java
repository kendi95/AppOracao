package com.apporacao.servicies;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.apporacao.dtos.PedidoOracaoDTO;
import com.apporacao.exceptions.AuthorizationException;
import com.apporacao.exceptions.ObjectNotFoundException;
import com.apporacao.exceptions.UserEqualsPedidoUserException;
import com.apporacao.model.PedidoOracao;
import com.apporacao.model.Usuario;
import com.apporacao.repositories.PedidoOracaoRepositorio;
import com.apporacao.repositories.UsuarioRepositorio;
import com.apporacao.security.UserDetailImplementation;
import com.apporacao.security.UserDetailServiceImplementation;


@Service
public class PedidoOracaoService {

	@Autowired
	private PedidoOracaoRepositorio repo;
	@Autowired
	private UsuarioRepositorio usuarioRepo;
	
	
	public void createPedido(PedidoOracaoDTO dto) {
		UserDetailImplementation user = UserDetailServiceImplementation.getAuthentication();
		if(user == null) {
			throw new AuthorizationException("Email não corresponde com o email de login");
		}
		Usuario usuario = usuarioRepo.findByEmail(user.getUsername());
		if(usuario == null) {
			throw new ObjectNotFoundException("Email não encontrado.");
		} else {
			PedidoOracao pedido = null;
			if(dto.getMotivoGeral() != null) {
				pedido = new PedidoOracao(null, usuario, dto.getMotivoGeral(), 
						null, null, dto.getIsAnonimo(), new Date(System.currentTimeMillis()));
				repo.save(pedido);
			} else {
				pedido = new PedidoOracao(null, usuario, null,  
						dto.getMotivoPessoal(), dto.getMotivoDescricao(), dto.getIsAnonimo(), new Date(System.currentTimeMillis()));
				repo.save(pedido);
			}
		}
	}
	
	public List<PedidoOracaoDTO> findAll(){
		UserDetailImplementation user = UserDetailServiceImplementation.getAuthentication();
		if(user == null) {
			throw new AuthorizationException("Email não corresponde com o email de login");
		} else {
			Usuario usuario = usuarioRepo.findByEmail(user.getUsername());
			List<PedidoOracaoDTO> dtos = findPedidos(usuario);
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
			} else {
				List<PedidoOracaoDTO> dtos = findPedidosByUsuarios(usuario);
				return dtos;
			}
		}
	}
	
	public void insertUsuariosIntoPedido(Long id) {
		UserDetailImplementation user = UserDetailServiceImplementation.getAuthentication();
		if(user == null) {
			throw new AuthorizationException("Email não corresponde com o email de login");
		} else {
			Usuario usuario = usuarioRepo.findByEmail(user.getUsername());
			if(usuario == null) {
				throw new ObjectNotFoundException("Email não encontrado.");
			} else {
				Optional<PedidoOracao> pedido = repo.findById(id);
				if(pedido.get().getUsuario().getId() == usuario.getId()) {
					throw new UserEqualsPedidoUserException("Usuário é o mesmo assóciado com o pedido.");
				} else {
					if(pedido.get().getUsuarios().isEmpty()) {
						pedido.get().getUsuarios().add(usuario);
						repo.save(pedido.get());
					} else {
						boolean isExists = false;
						for(Usuario u: pedido.get().getUsuarios()) {
							if(!u.getEmail().equals(usuario.getEmail())) {
								isExists = true;
								continue;
							} else {
								isExists = false;
								return;
							}
						}
						if(isExists == true) {
							pedido.get().getUsuarios().add(usuario);
							repo.save(pedido.get());
						} else {
							return;
						}
					}
				}
				
			}
		}
	}
	
	public void delete(Long id) {
		UserDetailImplementation user = UserDetailServiceImplementation.getAuthentication();
		if(user == null) {
			throw new AuthorizationException("Email não corresponde com o email de login");
		} else {
			repo.deleteById(id);
		}
	}
	
	
	public Page<PedidoOracaoDTO> search(Integer page, Integer linesPerPage, String direction){
		UserDetailImplementation user = UserDetailServiceImplementation.getAuthentication();
		if(user == null) {
			throw new AuthorizationException("Email não corresponde com o email de login");
		} else {
			Usuario usuario = usuarioRepo.findByEmail(user.getUsername());
			if(usuario == null) {
				throw new AuthorizationException("Email não corresponde com o email de login");
			}
			PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), "");
			
			Page<PedidoOracaoDTO> pages = searchPedido(usuario, pageRequest);
			return pages;
		}
	}
	
	
	
	
	private List<PedidoOracaoDTO> findPedidos(Usuario usuario){
		List<PedidoOracao> pedidos = repo.findAll();
		List<PedidoOracaoDTO> dtos = new ArrayList<>();
		PedidoOracaoDTO dto = null;
		for(PedidoOracao p: pedidos) {
			if(!usuario.getId().equals(p.getUsuario().getId())) {
				dto = new PedidoOracaoDTO(p);
				if(dto.getIsAnonimo() == true) {	
					dto.setNome_autor("Anônimo");
				} else {
					dto.setNome_autor(p.getUsuario().getNome());		
				}
				dtos.add(dto);
			} else {
				continue;
			}
		}
		return dtos;
	}
	
	private List<PedidoOracaoDTO> findPedidosByUsuarios(Usuario usuario){
		List<PedidoOracao> pedidos = repo.findByUsuario(usuario);
		List<PedidoOracaoDTO> dtos = new ArrayList<>();
		for(PedidoOracao p: pedidos) {
			if(p.getUsuario().getId() != null) {
				if(p.getUsuario().getId() == usuario.getId()) {
					PedidoOracaoDTO dto = new PedidoOracaoDTO(p);
					if(dto.getIsAnonimo() == true) {	
						dto.setNome_autor("Anônimo");
						dtos.add(dto);
					} else {
						dto.setNome_autor(p.getUsuario().getNome());
						dtos.add(dto);
					}
				}
			} else {
				continue;
			}
		}
		return dtos;
	}
	
	
	private Page<PedidoOracaoDTO> searchPedido(Usuario usuario, PageRequest pageRequest){
		Page<PedidoOracaoDTO> dtos = null;
		for(PedidoOracao page: repo.search(pageRequest)) {
			if(page.getUsuario().getId() != null) {
				if(page.getUsuario().getId() == usuario.getId()) {
					PedidoOracaoDTO dto = new PedidoOracaoDTO(page);
					if(dto.getIsAnonimo() == true) {
						dto.setNome_autor("Anônimo");
					} else {
						dto.setNome_autor(page.getUsuario().getNome());
					}
					dtos.getContent().add(dto);
				}
			} else {
				continue;
			}
		}
		return dtos;
	}
	
}

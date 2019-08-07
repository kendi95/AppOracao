package com.apporacao.servicies;

import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
import org.jasypt.iv.RandomIvGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.apporacao.dtos.ConviteDTO;
import com.apporacao.dtos.DefaultUsuarioDTO;
import com.apporacao.exceptions.AuthorizationException;
import com.apporacao.exceptions.EmailReceiverNotEqualException;
import com.apporacao.exceptions.ObjectNotFoundException;
import com.apporacao.exceptions.SQLViolationException;
import com.apporacao.exceptions.TimeExpirationException;
import com.apporacao.model.SuperUsuario;
import com.apporacao.model.Usuario;
import com.apporacao.model.enums.TipoUsuario;
import com.apporacao.repositories.SuperUsuarioRepositorio;
import com.apporacao.repositories.UsuarioRepositorio;
import com.apporacao.security.UserDetailImplementation;
import com.apporacao.security.UserDetailServiceImplementation;



@Service
public class UsuarioService {

	@Autowired
	private UsuarioRepositorio repo;
	@Autowired
	private SuperUsuarioRepositorio superUsuarioRepo;
	@Autowired
	private BCryptPasswordEncoder encoder;
	@Autowired
	private EmailServiceImpl emailServiceImpl;

	private StandardPBEStringEncryptor encrypt;
	
	
	
	
	public void insert(DefaultUsuarioDTO dto) {
		if(getExpiration(dto.getConviteEncrypt()) < new Date(System.currentTimeMillis()).getTime()) {
			throw new TimeExpirationException("O tempo do convite foi expirado.");
		} else {
			if(getRole(dto.getConviteEncrypt()).equalsIgnoreCase(TipoUsuario.COMUM.getTipo())) {
				if(getEmailReceiver(dto.getConviteEncrypt()).equalsIgnoreCase(dto.getEmail())) {
					SuperUsuario superUsuario =  superUsuarioRepo.findByEmail(getEmailSender(dto.getConviteEncrypt()));
					Usuario usuario = repo.findByEmail(dto.getEmail());
					if(usuario == null) {
						usuario = new Usuario(null, dto.getNome(), 
								dto.getEmail(), encoder.encode(dto.getSenha()), dto.getEstado(), dto.getCidade(), dto.getTelefone(), superUsuario);
						usuario.setTipo(TipoUsuario.COMUM);
						superUsuario.setUsuarios(Arrays.asList(usuario));
					
						repo.save(usuario);
					}
					throw new SQLViolationException("Não é possível inserir email existente.");
					
				}
				throw new EmailReceiverNotEqualException("O email não corresponde com o email do convite.");
				
			} else {
				SuperUsuario superUsuario = superUsuarioRepo.findByEmail(dto.getEmail());
				if(superUsuario == null) {
					superUsuario = new SuperUsuario(null, dto.getNome(), 
							dto.getEmail(), encoder.encode(dto.getSenha()), dto.getTelefone(), dto.getCidade(), dto.getEstado());
					superUsuario.setTipo(TipoUsuario.ADMIN);
					superUsuarioRepo.save(superUsuario);
				}
				throw new SQLViolationException("Não é possível inserir email existente.");
			}
		}
		
	}
	
	
	public void createConvite(ConviteDTO dto) {
		try {
			UserDetailImplementation user = UserDetailServiceImplementation.getAuthentication();
			if(user == null) {
				throw new AuthorizationException("Email não corresponde com o email de login");
			}
			String nameRemetente = superUsuarioRepo.findByEmail(dto.getEmailSender()).getNome();
			String nameDestino = repo.findByEmail(dto.getEmailReceiver()).getNome();
			pbeConfig();
			long timeExpiration = new Date(System.currentTimeMillis()+300000).getTime();
			String criptografado = encrypt.encrypt(dto.getEmailSender()+" "+dto.getEmailReceiver()+" "+dto.getTipo()+" "+timeExpiration);
			emailServiceImpl.sendEmailConvite(dto, "Convite", criptografado, nameRemetente, nameDestino);
		}catch(SocketTimeoutException e) {
			e.printStackTrace();
		}
		
	}
	
	public DefaultUsuarioDTO findByEmail() {
		UserDetailImplementation user = UserDetailServiceImplementation.getAuthentication();
		if(user == null) {
			throw new AuthorizationException("Email não corresponde com o email de login");
		}
		Usuario usuario = repo.findByEmail(user.getUsername());
		if(usuario == null) {
			SuperUsuario superUser = superUsuarioRepo.findByEmail(user.getUsername());
			if(superUser == null) {
				throw new ObjectNotFoundException("Email não encontrado");
			}
			return new DefaultUsuarioDTO(superUser);
		}
		return new DefaultUsuarioDTO(usuario);
	}
	
	public DefaultUsuarioDTO update(DefaultUsuarioDTO dto) {
		UserDetailImplementation user = UserDetailServiceImplementation.getAuthentication();
		if(user == null) {
			throw new AuthorizationException("Email não corresponde com o email de login");
		}
		Usuario usuario = repo.findByEmail(user.getUsername());
		if(usuario == null) {
			SuperUsuario superUser = superUsuarioRepo.findByEmail(user.getUsername());
			if(superUser == null) {
				throw new ObjectNotFoundException("Email não encontrado.");
			}
			superUser.setEmail(dto.getEmail());
			superUser.setTelefone(dto.getTelefone());
			superUser.setCidade(dto.getCidade());
			superUser.setEstado(dto.getEstado());
			return new DefaultUsuarioDTO(superUsuarioRepo.save(superUser));
		}
		usuario.setEmail(dto.getEmail());
		usuario.setTelefone(dto.getTelefone());
		usuario.setCidade(dto.getCidade());
		usuario.setEstado(dto.getEstado());
		return new DefaultUsuarioDTO(repo.save(usuario));
	}
	
	public List<DefaultUsuarioDTO> findAll(){
		List<DefaultUsuarioDTO> dtos = new ArrayList<>();
		List<Usuario> usuarios = repo.findAll();
		DefaultUsuarioDTO dto;
		for(Usuario u: usuarios) {
			dto = new DefaultUsuarioDTO(u);
			dtos.add(dto);
		}
		return dtos;
	}
	
	
	
	
	
	
	private String getEmailSender(String textEcrypt) {
		pbeConfig();
		String descrypt = encrypt.decrypt(textEcrypt);
		String[] emailSender = descrypt.split(" ");
		return emailSender[0];
	}
	
	private String getEmailReceiver(String textEcrypt) {
		pbeConfig();
		String descrypt = encrypt.decrypt(textEcrypt);
		String[] emailSender = descrypt.split(" ");
		return emailSender[1];
	}
	
	private String getRole(String textEcrypt) {
		pbeConfig();
		String descrypt = encrypt.decrypt(textEcrypt);
		String[] emailSender = descrypt.split(" ");
		return emailSender[2];
	}
	
	private long getExpiration(String textEcrypt) {
		pbeConfig();
		String descrypt = encrypt.decrypt(textEcrypt);
		String[] emailSender = descrypt.split(" ");
		Long expiration = Long.parseLong(emailSender[3]);
		return expiration;
	}
	
	
	
	private void pbeConfig() {
		encrypt = new StandardPBEStringEncryptor();
		encrypt.setAlgorithm("");
		encrypt.setPassword("");
		encrypt.setIvGenerator(new RandomIvGenerator());
	}
	
}

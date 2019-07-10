package com.apporacao.servicies;

import java.util.Arrays;
import java.util.Date;

import org.jasypt.util.text.BasicTextEncryptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.apporacao.dtos.ConviteDTO;
import com.apporacao.dtos.DefaultUsuarioDTO;
import com.apporacao.exceptions.AuthorizationException;
import com.apporacao.exceptions.EmailReceiverNotEqualException;
import com.apporacao.exceptions.ObjectNotFoundException;
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

	private BasicTextEncryptor encrypt;
	
	
	
	
	public void insert(DefaultUsuarioDTO dto) {
		if(getExpiration(dto.getConviteEncrypt()) < new Date(System.currentTimeMillis()).getTime()) {
			throw new TimeExpirationException("O tempo do convite foi expirado.");
		} else {
			if(getRole(dto.getConviteEncrypt()).equalsIgnoreCase(TipoUsuario.COMUM.getTipo())) {
				if(getEmailReceiver(dto.getConviteEncrypt()).equalsIgnoreCase(dto.getEmail())) {
					SuperUsuario superUsuario =  superUsuarioRepo.findByEmail(getEmailSender(dto.getConviteEncrypt()));
					Usuario usuario = new Usuario(null, dto.getNome(), 
							dto.getEmail(), encoder.encode(dto.getSenha()), dto.getEstado(), dto.getCidade(), dto.getTelefone(), superUsuario);
					usuario.setTipo(TipoUsuario.COMUM);
					superUsuario.setUsuarios(Arrays.asList(usuario));
					repo.save(usuario);
				}
				throw new EmailReceiverNotEqualException("O email não corresponde com o email do convite.");
				
			} else {
				SuperUsuario superUsuario = new SuperUsuario(null, dto.getNome(), 
						dto.getEmail(), encoder.encode(dto.getSenha()), dto.getTelefone(), dto.getCidade(), dto.getEstado());
				superUsuario.setTipo(TipoUsuario.ADMIN);
				superUsuarioRepo.save(superUsuario);
			}
		}
		
	}
	
	
	public void createConvite(ConviteDTO dto) {
		pbeConfig();
		long timeExpiration = new Date(System.currentTimeMillis()+300000).getTime();
		String criptografado = encrypt.encrypt(dto.getEmailSender()+" "+dto.getEmailReceiver()+" "+dto.getTipo()+" "+timeExpiration);
		emailServiceImpl.sendCustomMessage(dto.getEmailSender(), dto.getEmailReceiver(), "Convite", "Convite: "+criptografado);
	}
	
	public DefaultUsuarioDTO findByEmail(String email) {
		UserDetailImplementation user = UserDetailServiceImplementation.getAuthentication();
		if(user == null || !user.getUsername().equalsIgnoreCase(email)) {
			throw new AuthorizationException("Email não corresponde com o email de login");
		}
		Usuario usuario = repo.findByEmail(email);
		if(usuario == null) {
			SuperUsuario superUser = superUsuarioRepo.findByEmail(email);
			if(superUser == null) {
				throw new ObjectNotFoundException("Email não encontrado");
			}
			return new DefaultUsuarioDTO(superUser);
		}
		return new DefaultUsuarioDTO(usuario);
	}
	
	public DefaultUsuarioDTO update(DefaultUsuarioDTO dto, String email) {
		UserDetailImplementation user = UserDetailServiceImplementation.getAuthentication();
		if(user == null || !user.getUsername().equalsIgnoreCase(email)) {
			throw new AuthorizationException("Email não corresponde com o email de login");
		}
		Usuario usuario = repo.findByEmail(email);
		if(usuario == null) {
			SuperUsuario superUser = superUsuarioRepo.findByEmail(email);
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
	
	
	
	
	
	
	private String getEmailSender(String textEcrypt) {
		pbeConfig();
		String descrypt = encrypt.decrypt(textEcrypt);
		String[] emailSender = descrypt.split(" ");
		System.out.println(emailSender[0]);
		return emailSender[0];
	}
	
	private String getEmailReceiver(String textEcrypt) {
		pbeConfig();
		String descrypt = encrypt.decrypt(textEcrypt);
		String[] emailSender = descrypt.split(" ");
		System.out.println(emailSender[1]);
		return emailSender[1];
	}
	
	private String getRole(String textEcrypt) {
		pbeConfig();
		String descrypt = encrypt.decrypt(textEcrypt);
		String[] emailSender = descrypt.split(" ");
		System.out.println(emailSender[2]);
		return emailSender[2];
	}
	
	private long getExpiration(String textEcrypt) {
		pbeConfig();
		String descrypt = encrypt.decrypt(textEcrypt);
		String[] emailSender = descrypt.split(" ");
		System.out.println(emailSender[3]);
		Long expiration = Long.parseLong(emailSender[3]);
		return expiration;
	}
	
	
	
	private void pbeConfig() {
		encrypt = new BasicTextEncryptor();
		encrypt.setPassword("");
	}
	
}

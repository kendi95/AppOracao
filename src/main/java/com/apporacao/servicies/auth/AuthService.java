package com.apporacao.servicies.auth;

import java.util.Date;

import org.jasypt.util.text.BasicTextEncryptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.apporacao.dtos.SenhaDTO;
import com.apporacao.exceptions.ObjectNotFoundException;
import com.apporacao.exceptions.TimeExpirationException;
import com.apporacao.model.SuperUsuario;
import com.apporacao.model.Usuario;
import com.apporacao.repositories.SuperUsuarioRepositorio;
import com.apporacao.repositories.UsuarioRepositorio;
import com.apporacao.servicies.EmailServiceImpl;



@Service
public class AuthService {

	@Autowired
	private UsuarioRepositorio repo;
	@Autowired
	private SuperUsuarioRepositorio superUsuarioRepo;
	@Autowired
	private EmailServiceImpl emailServiceImpl;
	@Autowired
	private BCryptPasswordEncoder encoder;
	private BasicTextEncryptor encrypt;
	
	public void confirmEmail(String email) {
		Usuario usuario = repo.findByEmail(email);
		if(usuario == null) {
			SuperUsuario superUser = superUsuarioRepo.findByEmail(email);
			if(superUser == null) {
				throw new ObjectNotFoundException("Email não encontrado.");
			}
			createCodeSecurity(superUser.getEmail());
		}
		createCodeSecurity(usuario.getEmail());
	}
	
	public void newPassword(SenhaDTO dto) {
		if(getExpiration(dto.getCodeSecurity()) < new Date(System.currentTimeMillis()).getTime()) {
			throw new TimeExpirationException("O tempo do código de segurança foi expirado.");
		}
		Usuario usuario = repo.findByEmail(getEmail(dto.getCodeSecurity()));
		if(usuario == null) {
			SuperUsuario superUser = superUsuarioRepo.findByEmail(getEmail(dto.getCodeSecurity()));
			if(superUser == null) {
				throw new ObjectNotFoundException("Email não encontrado.");
			}
			superUser.setSenha(encoder.encode(dto.getSenha()));
			superUsuarioRepo.save(superUser);
		}
		usuario.setSenha(encoder.encode(dto.getSenha()));
		repo.save(usuario);
	}
	
	
	
	
	
	private void createCodeSecurity(String email) {
		pbeConfig();
		long timeExpiration = new Date(System.currentTimeMillis()+300000).getTime();
		String criptografado = encrypt.encrypt(email+" "+timeExpiration);
		emailServiceImpl.sendCustomMessage("kohatsukendi@gmail.com", email, "Código de segurança", "Código: "+criptografado);
	}
	
	
	
	private String getEmail(String textEcrypt) {
		pbeConfig();
		String descrypt = encrypt.decrypt(textEcrypt);
		String[] emailSender = descrypt.split(" ");
		return emailSender[0];
	}
	
	private long getExpiration(String textEcrypt) {
		pbeConfig();
		String descrypt = encrypt.decrypt(textEcrypt);
		String[] emailSender = descrypt.split(" ");
		Long expiration = Long.parseLong(emailSender[1]);
		return expiration;
	}
	
	
	
	private void pbeConfig() {
		encrypt = new BasicTextEncryptor();
		encrypt.setPassword("MessageEcryptByCurch");
	}
	
}

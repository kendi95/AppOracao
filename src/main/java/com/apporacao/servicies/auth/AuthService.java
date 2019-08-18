package com.apporacao.servicies.auth;

import java.net.SocketTimeoutException;
import java.util.Date;

import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
import org.jasypt.exceptions.EncryptionOperationNotPossibleException;
import org.jasypt.iv.RandomIvGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.apporacao.dtos.SenhaDTO;
import com.apporacao.exceptions.ObjectNotFoundException;
import com.apporacao.exceptions.TimeExpirationException;
import com.apporacao.model.Usuario;
import com.apporacao.repositories.UsuarioRepositorio;
import com.apporacao.servicies.EmailServiceImpl;

@Service
public class AuthService {

	@Autowired
	private UsuarioRepositorio repo;
	@Autowired
	private EmailServiceImpl emailServiceImpl;
	@Autowired
	private BCryptPasswordEncoder encoder;
	private StandardPBEStringEncryptor encrypt;
	
	private String email;
	private long expiration;
	
	
	public void confirmEmail(String email) {
		Usuario usuario = repo.findByEmail(email);
		if(usuario == null) {
			throw new ObjectNotFoundException("Email não encontrado.");
		} else {
			createCodeSecurity(usuario.getEmail());
		}
	}
	
	public void newPassword(SenhaDTO dto) {
		if(dto.getCodeSecurity() == null) {
			throw new EncryptionOperationNotPossibleException("Não há código de segurança inserido.");
		} else {
			getValuesEncrypts(dto.getCodeSecurity());
			if(expiration < new Date(System.currentTimeMillis()).getTime()) {
				throw new TimeExpirationException("O tempo do código de segurança foi expirado.");
			}
			Usuario usuario = repo.findByEmail(email);
			if(usuario == null) {
				throw new ObjectNotFoundException("Email não encontrado.");
			} else {
				usuario.setSenha(encoder.encode(dto.getSenha()));
				repo.save(usuario);
			}
		}
		
	}
	
	
	
	
	
	private void createCodeSecurity(String email) {
		try {
			pbeConfig();
			long timeExpiration = new Date(System.currentTimeMillis()+300000).getTime();
			String criptografado = encrypt.encrypt(email+" "+timeExpiration);
			emailServiceImpl.sendEmailNewPassword("kohatsukendi@gmail.com", email, "Código de segurança", criptografado);
		} catch (SocketTimeoutException e) {
			e.printStackTrace();
		}
	}
	
	private void getValuesEncrypts(String textEncrypt) {
		pbeConfig();
		String descrypt = encrypt.decrypt(textEncrypt);
		String[] textDecrypt = descrypt.split(" ");
		this.email = textDecrypt[0];
		this.expiration = Long.parseLong(textDecrypt[1]);
	}
	
	private void pbeConfig() {
		encrypt = new StandardPBEStringEncryptor();
		encrypt.setAlgorithm("");
		encrypt.setPassword("");
		encrypt.setIvGenerator(new RandomIvGenerator());
	}
	
}

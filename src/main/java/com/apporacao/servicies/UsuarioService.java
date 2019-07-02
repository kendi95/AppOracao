package com.apporacao.servicies;

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
import com.apporacao.exceptions.TimeExpirationException;
import com.apporacao.model.SuperUsuario;
import com.apporacao.model.Usuario;
import com.apporacao.model.enums.TipoUsuario;
import com.apporacao.repositories.SuperUsuarioRepositorio;
import com.apporacao.repositories.UsuarioRepositorio;

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
					Usuario usuario = new Usuario(null, dto.getNome(), 
							dto.getEmail(), encoder.encode(dto.getSenha()), dto.getEstado(), dto.getCidade(), dto.getTelefone(), superUsuario);
					usuario.setTipo(TipoUsuario.COMUM);
					superUsuario.setUsuarios(Arrays.asList(usuario));
					repo.save(usuario);
				}
				
			} else {
				SuperUsuario superUsuario = new SuperUsuario(null, dto.getNome(), 
						dto.getEmail(), encoder.encode(dto.getSenha()), dto.getTelefone(), dto.getCidade(), dto.getEstado());
				superUsuario.setTipo(TipoUsuario.ADMIN);
				superUsuarioRepo.save(superUsuario);
			}
		}
		
	}
	
	public List<String> findAllTipo(){
		List<String> tipos = new ArrayList<>();
		tipos.add(TipoUsuario.COMUM.getTipo());
		tipos.add(TipoUsuario.ADMIN.getTipo());
		return tipos;
	}
	
	public void createConvite(ConviteDTO dto) {
		pbeConfig();
		long timeExpiration = new Date(System.currentTimeMillis()+300000).getTime();
		String criptografado = encrypt.encrypt(dto.getEmailSender()+" "+dto.getEmailReceiver()+" "+dto.getTipo()+" "+timeExpiration);
		emailServiceImpl.sendCustomMessage(dto.getEmailSender(), dto.getEmailReceiver(), "Convite", "Convite: "+criptografado);
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
		System.out.println(emailSender[1]);
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
		encrypt = new StandardPBEStringEncryptor();
		encrypt.setPassword("");
		encrypt.setAlgorithm("");
		encrypt.setIvGenerator(new RandomIvGenerator());
	}
	
}

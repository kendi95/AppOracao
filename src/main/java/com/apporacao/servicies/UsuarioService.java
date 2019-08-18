package com.apporacao.servicies;

import java.net.SocketTimeoutException;
import java.util.Date;

import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
import org.jasypt.iv.RandomIvGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.apporacao.dtos.ConviteDTO;
import com.apporacao.dtos.DefaultUsuarioDTO;
import com.apporacao.exceptions.AuthorizationException;
import com.apporacao.exceptions.EmailReceiverNotEqualException;
import com.apporacao.exceptions.EmailSenderNotEqualException;
import com.apporacao.exceptions.ObjectNotFoundException;
import com.apporacao.exceptions.SQLViolationException;
import com.apporacao.exceptions.TimeExpirationException;
import com.apporacao.model.Usuario;
import com.apporacao.model.enums.TipoUsuario;
import com.apporacao.repositories.UsuarioRepositorio;
import com.apporacao.security.UserDetailImplementation;
import com.apporacao.security.UserDetailServiceImplementation;



@Service
public class UsuarioService {

	@Autowired
	private UsuarioRepositorio repo;
	@Autowired
	private BCryptPasswordEncoder encoder;
	@Autowired
	private EmailServiceImpl emailServiceImpl;

	private StandardPBEStringEncryptor encrypt;
	private String emailSender, emailReceiver, role;
	private long expiration;
	
		
	
	public void insert(DefaultUsuarioDTO dto) {
		getValuesEncrypts(dto.getConviteEncrypt());
		if(expiration < new Date(System.currentTimeMillis()).getTime()) {
			throw new TimeExpirationException("O tempo do convite foi expirado.");
		} else {
			if(emailReceiver.equalsIgnoreCase(dto.getEmail())) {
				Usuario usuario = repo.findByEmail(emailSender);
				if(usuario == null) {
					throw new EmailSenderNotEqualException("Não existe esse email.");
				} else {
					if(role.equalsIgnoreCase(TipoUsuario.COMUM.getTipo())) {
						
						insertUsuarioComum(dto);
					} else {
						insertUsuarioAdmin(dto);
					}
				}
			} else {
				throw new EmailReceiverNotEqualException("O email não corresponde com o email do convite.");
			}
		}
	}
	
	
	
	private void insertUsuarioComum(DefaultUsuarioDTO dto) {
		Usuario usuario = repo.findByEmail(dto.getEmail());
		if(usuario == null) {
			usuario = new Usuario(null, dto.getNome(), dto.getEmail(), encoder.encode(dto.getSenha()), 
					dto.getEstado(), dto.getCidade(), dto.getTelefone());
			usuario.setTipo(TipoUsuario.COMUM);
			repo.save(usuario);
		} else {
			throw new SQLViolationException("Não é possível inserir email existente.");
		}
	}
	
	private void insertUsuarioAdmin(DefaultUsuarioDTO dto) {
		Usuario usuario = repo.findByEmail(dto.getEmail());
		if(usuario == null) {
			usuario = new Usuario(null, dto.getNome(), dto.getEmail(), encoder.encode(dto.getSenha()), 
					dto.getEstado(), dto.getCidade(), dto.getTelefone());
			usuario.setTipo(TipoUsuario.ADMIN);
			repo.save(usuario);
		} else {
			throw new SQLViolationException("Não é possível inserir email existente.");
		}
	}
	
	
	
	public void createConvite(ConviteDTO dto) {
		try {
			UserDetailImplementation user = UserDetailServiceImplementation.getAuthentication();
			if(user == null) {
				throw new AuthorizationException("Email não corresponde com o email de login");
			} else {
				String nameRemetente = repo.findByEmail(dto.getEmailSender()).getNome();
				pbeConfig();
				long timeExpiration = new Date(System.currentTimeMillis()+300000).getTime();
				String criptografado = encrypt.encrypt(dto.getEmailSender()+" "+dto.getEmailReceiver()+" "+dto.getTipo()+" "+timeExpiration);
				emailServiceImpl.sendEmailConvite(dto, "Convite", criptografado, nameRemetente, dto.getEmailReceiver());
			}
		}catch(SocketTimeoutException e) {
			e.printStackTrace();
		}
		
	}
	
	
	private void getValuesEncrypts(String textEncrypt) {
		pbeConfig();
		String descrypt = encrypt.decrypt(textEncrypt);
		String[] textDecrypt = descrypt.split(" ");
		this.emailSender = textDecrypt[0];
		this.emailReceiver = textDecrypt[1];
		this.role = textDecrypt[2];
		this.expiration = Long.parseLong(textDecrypt[3]);
	}
	
	private void pbeConfig() {
		encrypt = new StandardPBEStringEncryptor();
		encrypt.setAlgorithm("");
		encrypt.setPassword("");
		encrypt.setIvGenerator(new RandomIvGenerator());
	}
	
	
	public DefaultUsuarioDTO findByEmail() {
		UserDetailImplementation user = UserDetailServiceImplementation.getAuthentication();
		if(user == null) {
			throw new AuthorizationException("Email não corresponde com o email de login");
		} else {
			Usuario usuario = repo.findByEmail(user.getUsername());
			if(usuario == null) {
				throw new ObjectNotFoundException("Email não encontrado");
			} else {
				return new DefaultUsuarioDTO(usuario);
			}
		}
	}
	
	public DefaultUsuarioDTO update(DefaultUsuarioDTO dto) {
		UserDetailImplementation user = UserDetailServiceImplementation.getAuthentication();
		if(user == null) {
			throw new AuthorizationException("Email não corresponde com o email de login");
		} else {
			Usuario usuario = repo.findByEmail(user.getUsername());
			if(usuario == null) {
				throw new ObjectNotFoundException("Email não encontrado.");
			} else {
				usuario.setEmail(dto.getEmail());
				usuario.setTelefone(dto.getTelefone());
				usuario.setCidade(dto.getCidade());
				usuario.setEstado(dto.getEstado());
				return new DefaultUsuarioDTO(repo.save(usuario));
			}
		}
	}
	
}

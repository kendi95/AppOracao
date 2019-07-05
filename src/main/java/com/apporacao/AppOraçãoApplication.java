package com.apporacao;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.apporacao.model.SuperUsuario;
import com.apporacao.model.Usuario;
import com.apporacao.model.enums.TipoUsuario;
import com.apporacao.repositories.SuperUsuarioRepositorio;
import com.apporacao.repositories.UsuarioRepositorio;

@SpringBootApplication
public class AppOraçãoApplication implements CommandLineRunner{

	@Autowired
	private UsuarioRepositorio repo;
	@Autowired
	private SuperUsuarioRepositorio superUsuarioRepo;
	@Autowired
	private BCryptPasswordEncoder encoder;
	
	
	public static void main(String[] args) {
		SpringApplication.run(AppOraçãoApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		
		SuperUsuario superUsuario = new SuperUsuario(null, "Tal Pastor", "pastor@gmail.com", encoder.encode("123"), 
				"43 99999-7777", "Londrina", "Paraná");
		superUsuario.setTipo(TipoUsuario.ADMIN);
		
		Usuario usuario = new Usuario(null, "Alisson", "alisson@gmail.com", encoder.encode("123"), 
				"Paraná", "Londrina", "43 99999-2222", superUsuario);
		usuario.setTipo(TipoUsuario.COMUM);
		
		superUsuarioRepo.save(superUsuario);
		repo.save(usuario);
	}

}

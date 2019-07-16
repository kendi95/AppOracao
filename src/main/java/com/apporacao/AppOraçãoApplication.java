package com.apporacao;



import java.util.Arrays;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.apporacao.model.MotivoGeral;
import com.apporacao.model.MotivoGeralDescricao;
import com.apporacao.model.PedidoOracao;
import com.apporacao.model.SuperUsuario;
import com.apporacao.model.Usuario;
import com.apporacao.model.enums.TipoUsuario;
import com.apporacao.repositories.MotivoGeralDescricaoRepositorio;
import com.apporacao.repositories.MotivoGeralRepositorio;
import com.apporacao.repositories.PedidoOracaoRepositorio;
import com.apporacao.repositories.SuperUsuarioRepositorio;
import com.apporacao.repositories.UsuarioRepositorio;

@SpringBootApplication
public class AppOraçãoApplication implements CommandLineRunner{

	@Autowired
	private UsuarioRepositorio repo;
	@Autowired
	private SuperUsuarioRepositorio superUsuarioRepo;
	@Autowired
	private PedidoOracaoRepositorio pedidoRepo;
	@Autowired
	private MotivoGeralRepositorio motivoGeralRepo;
	@Autowired
	private MotivoGeralDescricaoRepositorio mGDescricaoRepo;
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
		
		Usuario usuario = new Usuario(null, "Alisson", "kohatsukendi@gmail.com", encoder.encode("123"), 
				"Paraná", "Londrina", "43 99999-2222", superUsuario);
		usuario.setTipo(TipoUsuario.COMUM);
		
		PedidoOracao pedidoGeral = new PedidoOracao(null, superUsuario, usuario, "Motivo Geral", null, null, "true", 
				new Date(System.currentTimeMillis()));
		PedidoOracao pedidoPessoal = new PedidoOracao(null, superUsuario, usuario, null, "Motivo Pessoal", 
				"Descricao do motivo Descricao do motivo Descricao do motivo Descricao do motivo Descricao do motivo", "false", 
				new Date(System.currentTimeMillis()));
		
		MotivoGeral mGeral = new MotivoGeral(null, "familia");
		MotivoGeral mGeral2 = new MotivoGeral(null, "trabalho");
		
		MotivoGeralDescricao mGDescricao = new MotivoGeralDescricao(null, "Problema na família", mGeral);
		MotivoGeralDescricao mGDescricao2 = new MotivoGeralDescricao(null, "Problema no trabalho", mGeral2);
		
		mGeral.setmGDescricao(Arrays.asList(mGDescricao));
		mGeral2.setmGDescricao(Arrays.asList(mGDescricao2));
		
		superUsuarioRepo.save(superUsuario);
		repo.save(usuario);
		pedidoRepo.saveAll(Arrays.asList(pedidoGeral, pedidoPessoal));
		motivoGeralRepo.saveAll(Arrays.asList(mGeral, mGeral2));
		mGDescricaoRepo.saveAll(Arrays.asList(mGDescricao, mGDescricao2));
	}

}

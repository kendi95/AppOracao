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
		
		Usuario usuario2 = new Usuario(null, "William", "william@gmail.com", encoder.encode("123"), 
				"Paraná", "Londrina", "43 99999-2222", superUsuario);
		usuario2.setTipo(TipoUsuario.COMUM);
		
		Usuario usuario3 = new Usuario(null, "João", "joao@gmail.com", encoder.encode("123"), 
				"Paraná", "Londrina", "43 99999-2222", superUsuario);
		usuario3.setTipo(TipoUsuario.COMUM);
		
		
		
		PedidoOracao pedidoGeral = new PedidoOracao(null, usuario, "Motivo Geral", null, null, "true", 
				new Date(System.currentTimeMillis()));
		pedidoGeral.getUsuarios().addAll(Arrays.asList(usuario2, usuario3));
		
		PedidoOracao pedidoGeral2 = new PedidoOracao(null, usuario2, "Motivo Geral", null, null, "false", 
				new Date(System.currentTimeMillis()));
		pedidoGeral2.getUsuarios().addAll(Arrays.asList(usuario, usuario3));
		
		PedidoOracao pedidoGeral3 = new PedidoOracao(null, usuario3, "Motivo Geral", null, null, "true", 
				new Date(System.currentTimeMillis()));
		pedidoGeral3.getUsuarios().addAll(Arrays.asList(usuario, usuario2));
		
		PedidoOracao pedidoPessoal = new PedidoOracao(null, usuario, null, "Motivo Pessoal", 
				"Descricao do motivo Descricao do motivo Descricao do motivo Descricao do motivo Descricao do motivo", "false", 
				new Date(System.currentTimeMillis()));
		
		PedidoOracao pedidoPessoal2 = new PedidoOracao(null, usuario2, null, "Motivo Pessoal", 
				"Descricao do motivo Descricao do motivo Descricao do motivo Descricao do motivo Descricao do motivo", "true", 
				new Date(System.currentTimeMillis()));
		
		PedidoOracao pedidoPessoal3 = new PedidoOracao(null, usuario3, null, "Motivo Pessoal", 
				"Descricao do motivo Descricao do motivo Descricao do motivo Descricao do motivo Descricao do motivo", "false", 
				new Date(System.currentTimeMillis()));
		
		usuario.getPedidos().addAll(Arrays.asList(pedidoGeral, pedidoPessoal));
		usuario2.getPedidos().addAll(Arrays.asList(pedidoGeral2, pedidoPessoal2));
		usuario3.getPedidos().addAll(Arrays.asList(pedidoGeral3, pedidoPessoal3));
		
		
		MotivoGeral mGeral = new MotivoGeral(null, "familia");
		MotivoGeral mGeral2 = new MotivoGeral(null, "trabalho");
		
		
		MotivoGeralDescricao mGDescricao = new MotivoGeralDescricao(null, "Problema na família", mGeral);
		MotivoGeralDescricao mGDescricao2 = new MotivoGeralDescricao(null, "Problema no trabalho", mGeral2);
		
		mGeral.setmGDescricao(Arrays.asList(mGDescricao));
		mGeral2.setmGDescricao(Arrays.asList(mGDescricao2));
		
		superUsuarioRepo.save(superUsuario);
		repo.saveAll(Arrays.asList(usuario, usuario2, usuario3));
		pedidoRepo.saveAll(Arrays.asList(pedidoGeral, pedidoGeral2, pedidoGeral3, pedidoPessoal, pedidoPessoal2, pedidoPessoal3));
		motivoGeralRepo.saveAll(Arrays.asList(mGeral, mGeral2));
		mGDescricaoRepo.saveAll(Arrays.asList(mGDescricao, mGDescricao2));
	}

}

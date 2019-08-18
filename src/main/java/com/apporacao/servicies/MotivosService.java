package com.apporacao.servicies;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apporacao.dtos.MotivoGeralDescricaoDTO;
import com.apporacao.exceptions.AuthorizationException;
import com.apporacao.model.MotivoGeral;
import com.apporacao.repositories.MotivoGeralRepositorio;
import com.apporacao.security.UserDetailImplementation;
import com.apporacao.security.UserDetailServiceImplementation;

@Service
public class MotivosService {

	@Autowired
	private MotivoGeralRepositorio repo;
	
	
	
	public List<MotivoGeral> findAll(){
		return repo.findAll();
	}
	

	public List<MotivoGeralDescricaoDTO> findByDescricao(String descricao){
		UserDetailImplementation user = UserDetailServiceImplementation.getAuthentication();
		if(user == null) {
			throw new AuthorizationException("Email não corresponde com o email de login");
		}
		List<MotivoGeralDescricaoDTO> dtos = new ArrayList<>();
		MotivoGeralDescricaoDTO dto;
		MotivoGeral motivo = repo.findByDescricao(descricao);
		for(int i = 0; i < motivo.getmGDescricao().size(); i++) {
			dto = new MotivoGeralDescricaoDTO(motivo.getmGDescricao().get(i));
			dtos.add(dto);
		}
		return dtos;
	}
	
}

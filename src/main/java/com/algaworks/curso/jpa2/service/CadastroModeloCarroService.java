package com.algaworks.curso.jpa2.service;

import java.io.Serializable;

import javax.inject.Inject;

import com.algaworks.curso.jpa2.dao.ModeloCarroDAO;
import com.algaworks.curso.jpa2.model.ModeloCarro;
import com.algaworks.curso.jpa2.util.jpa.Transactional;

public class CadastroModeloCarroService implements Serializable {

	@Inject
	ModeloCarroDAO modeloCarroDAO;
	
	@Transactional
	public void save(ModeloCarro modelo) throws NegocioException {
		if(modelo.getDescricao() == null || modelo.getDescricao().trim().equals("")) {
			
			throw new NegocioException("O nome do modelo e obrigatório.");
		}
		if(modelo.getFabricante() == null) {
			throw new NegocioException("O fabricante e obrigatório");
		}
		this.modeloCarroDAO.save(modelo);
	}
	
}

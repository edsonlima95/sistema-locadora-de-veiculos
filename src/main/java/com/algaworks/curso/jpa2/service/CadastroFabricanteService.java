package com.algaworks.curso.jpa2.service;

import java.io.Serializable;

import javax.inject.Inject;

import com.algaworks.curso.jpa2.dao.FabricanteDAO;
import com.algaworks.curso.jpa2.model.Fabricante;
import com.algaworks.curso.jpa2.util.jpa.Transactional;

/*Esta classe e especialista para regras de negocios, validacoes e etc.*/
public class CadastroFabricanteService implements Serializable {

	// Injeta o DAO do fabricante.
	@Inject
	private FabricanteDAO fabricanteDAO;

	@Transactional
	public void save(Fabricante fabricante) throws NegocioException {

		//Caso o nome esteja em branco ou com espaços
		if (fabricante.getNome() == null || fabricante.getNome().trim().equals("")) {
			throw new NegocioException("O nome do fabricante deve ser informado!");
		}
		
		//Cadastra o fabricante.
		this.fabricanteDAO.save(fabricante);

	}

}

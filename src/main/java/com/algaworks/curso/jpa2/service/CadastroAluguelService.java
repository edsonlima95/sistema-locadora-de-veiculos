package com.algaworks.curso.jpa2.service;

import java.io.Serializable;
import java.text.DateFormat;
import java.util.Calendar;

import javax.inject.Inject;

import com.algaworks.curso.jpa2.dao.AluguelDAO;
import com.algaworks.curso.jpa2.model.Aluguel;
import com.algaworks.curso.jpa2.util.jpa.Transactional;

public class CadastroAluguelService implements Serializable {

	private static final long serialVersionUID = 1L;
	private Calendar dataHoje = Calendar.getInstance();

	
	
	@Inject
	private AluguelDAO aluguelDAO;
	
	@Transactional
	public void salvar(Aluguel aluguel) throws NegocioException {
		
		if (aluguel.getCarro() == null) {
			throw new NegocioException("O carro é obrigatório");
		}
		
		aluguel.setDataPedido(this.dataHoje);//Seta a data de hoje.
		this.aluguelDAO.salvar(aluguel);
		
	}

}

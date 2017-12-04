package com.algaworks.curso.jpa2.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.algaworks.curso.jpa2.dao.MotoristaDAO;
import com.algaworks.curso.jpa2.model.Carro;
import com.algaworks.curso.jpa2.model.Motorista;
import com.algaworks.curso.jpa2.service.NegocioException;
import com.algaworks.curso.jpa2.util.jsf.FacesUtil;

@Named
@ViewScoped
public class PesquisaMotoristaBean implements Serializable {

	@Inject
	private MotoristaDAO motoristaDAO;

	private List<Motorista> motoristas = new ArrayList();
	private Motorista motoristaSelecionado;

	@PostConstruct
	public void inicializar() {
		this.motoristas = motoristaDAO.buscarTodos();
	}

	public void excluir() {
		try {
			motoristaDAO.excluir(motoristaSelecionado);
			this.motoristas.remove(motoristaSelecionado);
			FacesUtil.addSuccessMessage("O motorista " + motoristaSelecionado.getNome() + " foi excluído com sucesso.");
		} catch (NegocioException e) {
			FacesUtil.addErrorMessage(e.getMessage());
		}
	}

	public List<Motorista> getMotoristas() {
		return motoristas;
	}

	public void setMotoristas(List<Motorista> motoristas) {
		this.motoristas = motoristas;
	}

	public Motorista getMotoristaSelecionado() {
		return motoristaSelecionado;
	}

	public void setMotoristaSelecionado(Motorista motoristaSelecionado) {
		this.motoristaSelecionado = motoristaSelecionado;
	}
}

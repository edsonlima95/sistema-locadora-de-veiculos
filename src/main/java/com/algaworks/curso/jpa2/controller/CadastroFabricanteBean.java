package com.algaworks.curso.jpa2.controller;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.algaworks.curso.jpa2.model.Fabricante;
import com.algaworks.curso.jpa2.service.CadastroFabricanteService;
import com.algaworks.curso.jpa2.service.NegocioException;
import com.algaworks.curso.jpa2.util.jsf.FacesUtil;

@Named
@ViewScoped
public class CadastroFabricanteBean implements Serializable {

	@Inject
	private CadastroFabricanteService cadastroFabricanteService;
	private Fabricante fabricante;

	
	@PostConstruct
	public void init() {
		this.fabricante = new Fabricante();//Limpa o objeto.
	}

	public void salvar() {
		try {
			this.cadastroFabricanteService.save(this.fabricante);
			FacesUtil.addSuccessMessage("Fabricante salvo com sucesso!");
		} catch (NegocioException e) {
			FacesUtil.addErrorMessage(e.getMessage());
		}
		this.Limpar();
	}
	
	public void Limpar() {
		this.fabricante = new Fabricante();
	}

	public Fabricante getFabricante() {
		return fabricante;
	}

	public void setFabricante(Fabricante fabricante) {
		this.fabricante = fabricante;
	}

}

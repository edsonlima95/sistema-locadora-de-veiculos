package com.algaworks.curso.jpa2.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.algaworks.curso.jpa2.model.Acessorio;
import com.algaworks.curso.jpa2.model.Carro;
import com.algaworks.curso.jpa2.model.ModeloCarro;
import com.algaworks.curso.jpa2.model.Motorista;
import com.algaworks.curso.jpa2.model.Sexo;
import com.algaworks.curso.jpa2.service.CadastroMotoristaService;
import com.algaworks.curso.jpa2.service.NegocioException;
import com.algaworks.curso.jpa2.util.jsf.FacesUtil;

@Named
@ViewScoped
public class CadastroMotoristaBean implements Serializable {

	private Motorista motorista =  new Motorista();
	private List<Sexo> sexos = new ArrayList();
	@Inject
	private CadastroMotoristaService cadastroMotoristaService;
	
	@PostConstruct
	public void inicializar() {
		this.limpar();
		this.sexos = Arrays.asList(Sexo.values());
	}
	
	public void salvar() {
		try {
			//Salva um carro.
			this.cadastroMotoristaService.salvar(motorista);
			FacesUtil.addSuccessMessage("Motorista salvo com sucesso!");
		} catch (NegocioException e) {
			FacesUtil.addErrorMessage(e.getMessage());
		} 
		
		this.limpar();
	}
	
	public void limpar() {
		this.motorista = new Motorista();
	}

	public Motorista getMotorista() {
		return motorista;
	}

	public void setMotorista(Motorista motorista) {
		this.motorista = motorista;
	}

	public List<Sexo> getSexos() {
		return sexos;
	}	

}

package com.algaworks.curso.jpa2.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.algaworks.curso.jpa2.dao.FabricanteDAO;
import com.algaworks.curso.jpa2.model.Categoria;
import com.algaworks.curso.jpa2.model.Fabricante;
import com.algaworks.curso.jpa2.model.ModeloCarro;
import com.algaworks.curso.jpa2.service.CadastroModeloCarroService;
import com.algaworks.curso.jpa2.service.NegocioException;
import com.algaworks.curso.jpa2.util.jsf.FacesUtil;

@Named
@ViewScoped
public class CadastroModeloCarroBean implements Serializable {

	
	@Inject
	private CadastroModeloCarroService cadastroModeloCarroService;
	private ModeloCarro modeloCarro = new ModeloCarro();
	private List<Fabricante> fabricantes = new ArrayList();
	private List<Categoria> categorias = new ArrayList();
	@Inject
	private FabricanteDAO fabricanteDAO;
	
	@PostConstruct
	public void init() {
		this.fabricantes = fabricanteDAO.buscarTodos();
		this.categorias = Arrays.asList(Categoria.values());//Recebe o enum de categorias.
	}
	
	public void salvar() {
		try {
			this.cadastroModeloCarroService.save(this.modeloCarro);
			FacesUtil.addSuccessMessage("Modelo carro salvo com sucesso.");
		} catch (NegocioException e) {
			FacesUtil.addErrorMessage(e.getMessage());
		}
		if(this.modeloCarro == null) {			
			this.limpar();
		}
	} 
	
	public void limpar() {
		this.modeloCarro = new ModeloCarro();
	}
	
	
	public ModeloCarro getModeloCarro() {
		return modeloCarro;
	}

	public void setModeloCarro(ModeloCarro modeloCarro) {
		this.modeloCarro = modeloCarro;
		if(this.modeloCarro == null) {
			this.modeloCarro = new ModeloCarro();
		}
	}

	public List<Fabricante> getFabricantes() {
		return fabricantes;
	}

	public List<Categoria> getCategorias() {
		return categorias;
	}

	public void setCategorias(List<Categoria> categorias) {
		this.categorias = categorias;
	}
	
}

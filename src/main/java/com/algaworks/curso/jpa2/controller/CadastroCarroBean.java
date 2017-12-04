package com.algaworks.curso.jpa2.controller;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.apache.commons.io.IOUtils;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;

import com.algaworks.curso.jpa2.dao.AcessorioDAO;
import com.algaworks.curso.jpa2.dao.ModeloCarroDAO;
import com.algaworks.curso.jpa2.model.Acessorio;
import com.algaworks.curso.jpa2.model.Carro;
import com.algaworks.curso.jpa2.model.ModeloCarro;
import com.algaworks.curso.jpa2.service.CadastroCarroService;
import com.algaworks.curso.jpa2.service.NegocioException;
import com.algaworks.curso.jpa2.util.jsf.FacesUtil;

@Named
@ViewScoped
public class CadastroCarroBean implements Serializable {

	private Carro carro = new Carro();

	// Lista todos os modelos cadastrados.
	private List<ModeloCarro> modelosCarros = new ArrayList();
	// Lista todos os acessorios cadastrados.
	private List<Acessorio> acessorios = new ArrayList();

	private UploadedFile uploadedFile;// imagem

	@Inject
	private CadastroCarroService cadastroCarroService;

	@Inject
	private AcessorioDAO acessorioDAO;

	@Inject
	private ModeloCarroDAO modeloCarroDAO;

	@PostConstruct
	public void inicializar() {
		this.limpar();
		// Recebe a lista dos acessorios
		this.acessorios = acessorioDAO.buscarTodos();
		// Recebe a lista dos modelos de carro cadastrados
		this.modelosCarros = modeloCarroDAO.buscarTodos();
	}

	public void salvar() {
		try {
			// Salva a foto.

			if (this.uploadedFile != null) {
				byte [] conteudo = this.uploadedFile.getContents();
				this.carro.setFoto(conteudo);// Seta a foto no carro.
			}

			// Salva um carro.
			this.cadastroCarroService.salvar(carro);
			FacesUtil.addSuccessMessage("Carro salvo com sucesso!");
		} catch (NegocioException e) {
			FacesUtil.addErrorMessage(e.getMessage());
		}

		this.limpar();
	}

	public void limpar() {
		this.carro = new Carro();
		// this.carro.setAcessorios(new ArrayList<Acessorio>());
	}

	public Carro getCarro() {
		return carro;
	}

	public void setCarro(Carro carro) {
		this.carro = carro;
	}

	public List<Acessorio> getAcessorios() {
		return acessorios;
	}

	public List<ModeloCarro> getModelosCarros() {
		return modelosCarros;
	}

	public UploadedFile getUploadedFile() {
		return uploadedFile;
	}

	public void setUploadedFile(UploadedFile uploadedFile) {
		this.uploadedFile = uploadedFile;
	}

}

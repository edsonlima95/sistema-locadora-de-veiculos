package com.algaworks.curso.jpa2.dao;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;

import com.algaworks.curso.jpa2.model.ModeloCarro;
import com.algaworks.curso.jpa2.service.NegocioException;
import com.algaworks.curso.jpa2.util.jpa.Transactional;

public class ModeloCarroDAO implements Serializable {

	@Inject
	private EntityManager em;

	public void save(ModeloCarro modelo) {
		em.merge(modelo);
	}

	public List<ModeloCarro> buscarTodos() {
		return em.createQuery("from ModeloCarro").getResultList();
	}

	@Transactional
	public void excluir(ModeloCarro modeloSelecionado) throws NegocioException {
		/*
		 * Para deletar tem que buscar o fabricante pelo codigo do selecionado pq o
		 * EntityManager quando executa o listar, ele acaba, assim ao tentar excluir sem
		 * buscar da um erro detached
		 */
		ModeloCarro modelo = em.find(ModeloCarro.class, modeloSelecionado.getCodigo());
		em.remove(modelo);
	}

	public ModeloCarro buscarPeloCodigo(Long codigo) {
		return em.find(ModeloCarro.class, codigo);
	}

}

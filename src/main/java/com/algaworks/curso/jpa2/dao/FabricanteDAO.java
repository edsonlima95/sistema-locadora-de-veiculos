package com.algaworks.curso.jpa2.dao;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;

import com.algaworks.curso.jpa2.model.Fabricante;
import com.algaworks.curso.jpa2.service.NegocioException;
import com.algaworks.curso.jpa2.util.jpa.Transactional;

public class FabricanteDAO implements Serializable {
	
	@Inject
	private EntityManager em;
	
	public void save(Fabricante fabricante) {
		em.merge(fabricante);
	}
	
	public List<Fabricante> buscarTodos() {
		return em.createQuery("from Fabricante").getResultList();
	}

	@Transactional
	public void excluir(Fabricante fabricanteSelecionado) throws NegocioException {
		/*Para deletar tem que buscar o fabricante pelo codigo do selecionado
		 * pq o EntityManager quando executa o listar, ele acaba, assim ao tentar excluir sem 
		 * buscar da um erro detached */
		Fabricante fabricante = em.find(Fabricante.class, fabricanteSelecionado.getCodigo());
		em.remove(fabricante);
	}

	public Fabricante buscarPorCodigo(Long codigo) {
		return em.find(Fabricante.class, codigo);
	} 
	
}

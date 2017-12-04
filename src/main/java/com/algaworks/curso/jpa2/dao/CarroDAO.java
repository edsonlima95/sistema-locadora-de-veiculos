package com.algaworks.curso.jpa2.dao;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;

import com.algaworks.curso.jpa2.model.Carro;
import com.algaworks.curso.jpa2.service.NegocioException;
import com.algaworks.curso.jpa2.util.jpa.Transactional;

public class CarroDAO implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Inject
	private EntityManager manager;
	
	public Carro buscarPeloCodigo(Long codigo) {
		return  manager.find(Carro.class, codigo);
	}
	
	public void salvar(Carro fabricante) {
		manager.merge(fabricante);
	}

	@SuppressWarnings("unchecked")
	public List<Carro> buscarTodos() {
		//Executa a namedQuery da entidade Carro.
		return manager.createNamedQuery("Carro.buscarTodos").getResultList();
	}
	
	@Transactional
	public void excluir(Carro carro) throws NegocioException {
		carro = buscarPeloCodigo(carro.getCodigo());
		try {
			manager.remove(carro);
			manager.flush();
		} catch (PersistenceException e) {
			throw new NegocioException("Carro não pode ser excluído.");
		}
	}
	
	//Busca acessorios pelo codigo do carro.
	public Carro buscarCarroComAcessorios(Long codigo) {
		return manager.createNamedQuery("Carro.buscarCarroComAcessorios",Carro.class)
				.setParameter("codigo", codigo)
				.getSingleResult();
	}

	//Retorna uma lista de carros com limite e tamanho.
	@SuppressWarnings("unchecked")
	public List<Carro> buscarComPaginacao(int first, int pageSize) {
		return manager.createNamedQuery("Carro.buscarTodos")
										.setFirstResult(first)
										.setMaxResults(pageSize)
										.getResultList();
	}

	//Retorna a quantidade de carros.
	public Long encontrarQuantidadeDeCarros() {
		return manager.createQuery("SELECT count(c) FROM Carro c",Long.class).getSingleResult();
	}
	//Retorna uma lista de acessorios.
	public List<String> acessoriosDescricao(Long codigo){
		return manager.createQuery("select a.descricao from Carro c join c.acessorios a where c.codigo = :codigo")
						.setParameter("codigo", codigo)
						.getResultList();
	}
}

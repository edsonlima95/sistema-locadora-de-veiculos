package com.algaworks.jpa2.criteria;

import javax.persistence.CascadeType;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.algaworks.curso.jpa2.model.Carro;
import com.algaworks.curso.jpa2.model.Categoria;
import com.algaworks.curso.jpa2.model.ModeloCarro;

public class ExemplosCascata {

	private static EntityManagerFactory factory;
	private EntityManager manager;

	@BeforeClass
	public static void init() {
		factory = Persistence.createEntityManagerFactory("locadoraVeiculoPU");
	}

	@Before
	public void setUp() {
		this.manager = this.factory.createEntityManager();
	}

	@After
	public void tearDown() {
		this.manager.close();
	}

	@Test
	public void exemploGravacaoCascata() {

		Carro carro = new Carro();
		carro.setCor("Azul teste");
		carro.setPlaca("VVV-09382");

		/*
		 * Ao tentar cadastrar o modelo o jpq nao ira conseguir persistir pois ele nao
		 * reconhece esse novo objeto criado. para resolver o erro apenas coloca um
		 * anotacao Cascade (cascade=CascadeType.PERSIST) no atritubo na entidade nesse caso la em Carro pq to
		 * tentando salvar um carro e o modelo esta dentro dele
		 */
		ModeloCarro modelo = new ModeloCarro();
		modelo.setDescricao("Cadastro cascata");
		modelo.setCategoria(Categoria.ESPORTIVO);
		carro.setModelo(modelo);

		this.manager.getTransaction().begin();

		this.manager.persist(carro);

		this.manager.getTransaction().commit();

	}

	@Test
	public void exemploDeleteCascata() {

		Carro carro = this.manager.find(Carro.class, 1L);// Busca carro pelo codigo

		// System.out.println("nome: " + carro.getCor());
		/*
		 * Para deletar um carro que o id e referencia para outra tabela e so usa o
		 * cascade=CascadeType.REMOVE.
		 * 
		 */
		manager.getTransaction().begin();
		this.manager.remove(carro);
		manager.getTransaction().commit();

	}

}

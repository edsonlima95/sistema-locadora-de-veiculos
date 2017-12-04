package com.algaworks.jpa2.criteria;

import java.math.BigDecimal;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Tuple;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Subquery;

import org.hibernate.query.criteria.internal.expression.function.AggregationFunction.SUM;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.algaworks.curso.jpa2.model.Aluguel;
import com.algaworks.curso.jpa2.model.Carro;
import com.algaworks.curso.jpa2.model.ModeloCarro;

public class ExemplosCriteria {
	
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

	/*Retorna apenas um valor do atributo e nao a entidade toda, nesse caso a placa.*/
	@Test
	public void projecoes() {
		CriteriaBuilder builder = this.manager.getCriteriaBuilder();
		CriteriaQuery<String> criteriaQuery = builder.createQuery(String.class);
		
		Root<Carro> carro = criteriaQuery.from(Carro.class);
		criteriaQuery.select(carro.get("placa"));//Retorna apenas a placa.
		
		TypedQuery<String> query = manager.createQuery(criteriaQuery);
		List<String> placas = query.getResultList();
		
		for(String p : placas) {
			System.out.println("Placa: " + p);
		}
	}
	
	/*Retorna dois atributos apenas a placa e o valor diario. 
	 * Tipo e um array de Object[]*/
	@Test
	public void resultadoComplexo() {
		CriteriaBuilder builder = this.manager.getCriteriaBuilder();
		CriteriaQuery<Object[]> criteriaQuery = builder.createQuery(Object[].class);
		
		Root<Carro> carro = criteriaQuery.from(Carro.class);
		criteriaQuery.multiselect(carro.get("placa"), carro.get("valorDiaria"));
		
		TypedQuery<Object[]> query = manager.createQuery(criteriaQuery);//Cria o array.
		List<Object[]> result = query.getResultList();//Retorna um array com os dois campos.
		
		for(Object[] res : result) {
			System.out.println("Placa: " + res[0]);
			System.out.println("Valor diaria: " + res[1] + "\n");
			
		}
	}
	/*Retorna dois atributos apenas a placa e o valor diario. 
	 * Tipo e Tuple, vc indica a chave e depois reculpera os valores.*/
	@Test
	public void resultadoTuple() {
		CriteriaBuilder builder = this.manager.getCriteriaBuilder();
		CriteriaQuery<Tuple> criteriaQuery = builder.createQuery(Tuple.class);
		
		Root<Carro> carro = criteriaQuery.from(Carro.class);
		criteriaQuery.multiselect(carro.get("placa").alias("placaValor"),carro.get("valorDiaria").alias("diariaValor"));
		
		TypedQuery<Tuple> query = manager.createQuery(criteriaQuery);
		
		List<Tuple> results = query.getResultList();
		
		for(Tuple res : results) {
			System.out.println("Placa : " + res.get("placaValor"));	
			System.out.println("Valor da diaria : " + res.get("diariaValor") + "\n");	
		}
	}
	
	/*Retorna dois atributos apenas a placa e o valor diario. 
	 * Tipo e PrecoCarro que e outra classe atraves do construtor
	 * recebe os valores.*/
	@Test
	public void resultadoParaOutraClasse() {
		CriteriaBuilder builder = this.manager.getCriteriaBuilder();
		CriteriaQuery<PrecoCarro> criteriaQuery = builder.createQuery(PrecoCarro.class);
		
		Root<Carro> carro = criteriaQuery.from(Carro.class);
		criteriaQuery.select(builder.construct(PrecoCarro.class,carro.get("placa"), carro.get("valorDiaria")));//Joga os valores no construtor da classe.
		
		TypedQuery<PrecoCarro> query = manager.createQuery(criteriaQuery);
		
		List<PrecoCarro> results = query.getResultList();
		
		for(PrecoCarro res : results) {
			System.out.println("Placa : " + res.getPlaca());	
			System.out.println("Valor da diaria : " + res.getValorDiaria() + "\n");	
		}
	}
	
	/*Adicionando uma funcão na pesquisa, neste exemplo o upperCase que ira
	 * busca os carros pela cor indicada. Sera usado um predicate.*/
	@Test
	public void exemploFuncao() {
		CriteriaBuilder builder = this.manager.getCriteriaBuilder();
		CriteriaQuery<Carro> criteriaQuery = builder.createQuery(Carro.class);
		
		Root<Carro> carro = criteriaQuery.from(Carro.class);
		Predicate predicate = builder.equal(builder.upper(carro.get("cor")),"cinza".toUpperCase());
		
		criteriaQuery.select(carro);
		criteriaQuery.where(predicate);
		
		List<Carro> carros = manager.createQuery(criteriaQuery).getResultList();
		
		for(Carro c : carros) {
			System.out.println("Modelo: "+ c.getModelo().getDescricao());
			System.out.println("Cor: " + c.getCor() + "\n");
		}
	}
	
	/*Exemplo usando o join e o fetch, o fetch e para que quando buscar o carro
	 * ja trazer o modelo junto e nao em outra consulta*/
	@Test
	public void exemploJoinFetch() {
		CriteriaBuilder builder = this.manager.getCriteriaBuilder();
		CriteriaQuery<Carro> criteriaQuery = builder.createQuery(Carro.class);
		
		Root<Carro> carro = criteriaQuery.from(Carro.class);
		Join<Carro, ModeloCarro> modelo = (Join) carro.fetch("modelo");
		
		TypedQuery<Carro> query = manager.createQuery(criteriaQuery);
		List<Carro> carros = query.getResultList();
		
		for(Carro c: carros) {
			System.out.println("Placa: " + c.getPlaca());
			System.out.println("Modelo: " + c.getModelo().getDescricao());
		}
	}
	
	/*SubQueries: faz uma consulta para retornar a avg e depois 
	 * outra consulta para compara se tem algum carro com valor acima da media*/
	@Test
	public void exemploSubqueries() {
		CriteriaBuilder builder = this.manager.getCriteriaBuilder();
		CriteriaQuery<Carro> criteriaQuery = builder.createQuery(Carro.class);
		//Cria a subquery
		Subquery<Double> subquery = criteriaQuery.subquery(Double.class);
		
		Root<Carro> carro = criteriaQuery.from(Carro.class);
		Root<Carro> carroSub = subquery.from(Carro.class);//Cria o root para a subquery.
		
		subquery.select(builder.avg(carroSub.get("valorDiaria")));//Traz a media dos valores.
		
		criteriaQuery.select(carro);
		criteriaQuery.where(builder.greaterThanOrEqualTo(carro.get("valorDiaria"), subquery));//compara se o valorDiario e maior ou igual a media.
	
		TypedQuery<Carro> query = manager.createQuery(criteriaQuery);
		List<Carro> results = query.getResultList();
		
		for(Carro c : results) {
			System.out.println("Placa: " + c.getPlaca() + " - " + c.getValorDiaria());
		}
	}
	
	//Para executar apenas esse metodo, ctrl shift x e escolher.
	/*Nessa consulta sera retornado a soma de todos os valores do campo valorTotal*/
	@Test
	public void funcoesAgregacao() {
		CriteriaBuilder builder = this.manager.getCriteriaBuilder();
		CriteriaQuery<BigDecimal> criteriaQuery = builder.createQuery(BigDecimal.class);
		
		Root<Aluguel> aluguel = criteriaQuery.from(Aluguel.class);
		criteriaQuery.select(builder.sum(aluguel.get("valorTotal")));//Traz a soma dos valores
		
		
		BigDecimal total = manager.createQuery(criteriaQuery).getSingleResult();
		
		System.out.println("Soma de todos os alugueis: " + total);
	}
}

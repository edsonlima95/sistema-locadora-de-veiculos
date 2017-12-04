package com.algaworks.curso.jpa2.dao;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.ParameterExpression;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import com.algaworks.curso.jpa2.model.Aluguel;
import com.algaworks.curso.jpa2.model.ModeloCarro;

public class AluguelDAO implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Inject
	private EntityManager manager;
	
	public void salvar(Aluguel aluguel) {
		manager.merge(aluguel);
	}

	public List<Aluguel> buscarPorDataDeEntregaEModeloCarro(Date dataEntrega, ModeloCarro modelo) {
		
		//CRITERIA BUILDER.
		CriteriaBuilder builder = manager.getCriteriaBuilder();//Constroi a builder.
		CriteriaQuery<Aluguel> criteriaQuery = builder.createQuery(Aluguel.class);//Cria a query.
		Root<Aluguel> a = criteriaQuery.from(Aluguel.class);//SELECT a FROM Aluguel a;
		criteriaQuery.select(a);
		
		//Cria uma lista de predicados.
		List<Predicate> predicates = new ArrayList<>();
		
		//Verifica se a data foi digitada no campo e cria os parametros.
		if(dataEntrega != null) {
			//Aqui e como se fosse :dataEntregaInicial para o where do jpql.
			ParameterExpression<Date> dataEntregaInicial = builder.parameter(Date.class,"dataEntregaInicial");
			ParameterExpression<Date> dataEntregaFinal = builder.parameter(Date.class,"dataEntregaFinal");
			
			predicates.add(builder.between(a.get("dataEntrega"), dataEntregaInicial, dataEntregaFinal));//Retorna o valor entre as datas
		}
		
		//Verifica se o modelo foi selecionado e cria os parametros.
		if(modelo != null) {
			ParameterExpression<ModeloCarro> modeloExpression = builder.parameter(ModeloCarro.class,"modelo");
			predicates.add(builder.equal(a.get("carro").get("modelo"),modeloExpression ));//Navega na entidade carro e acessa o modelo.
		}
		
		//CRIA A QUERY.
		criteriaQuery.where(predicates.toArray(new Predicate[0]));
		TypedQuery<Aluguel> query = manager.createQuery(criteriaQuery);//Faz o cast e cria a query.
		
		//RESETA A HORA, MINUTO E SEGUNDO DA DATA INFORMADA E SETA OS PARAMETROS.
		if(dataEntrega != null) {
			Calendar dataEntregaInicial = Calendar.getInstance();//Pega a data.
			dataEntregaInicial.setTime(dataEntrega);//Seta a data da entrega informada.
			dataEntregaInicial.set(Calendar.HOUR_OF_DAY, 0);
			dataEntregaInicial.set(Calendar.MINUTE, 0);
			dataEntregaInicial.set(Calendar.SECOND, 0);
			
			Calendar dataEntregaFinal = Calendar.getInstance();//Pega a data.
			dataEntregaFinal.setTime(dataEntrega);//Seta a data da entrega informada.
			//Seta ate meia noite do outro dia.
			dataEntregaFinal.set(Calendar.HOUR_OF_DAY, 23);
			dataEntregaFinal.set(Calendar.MINUTE, 59);
			dataEntregaFinal.set(Calendar.SECOND, 59);
			
			//Seta os parametros.
			query.setParameter("dataEntregaInicial", dataEntregaInicial.getTime());
			query.setParameter("dataEntregaFinal", dataEntregaFinal.getTime());
		}
		
		//SETA O PARAMETRO MODELO
		if(modelo != null) {
			query.setParameter("modelo",modelo);
		}
		
		return query.getResultList();//Retorna os resultados.
	}

}

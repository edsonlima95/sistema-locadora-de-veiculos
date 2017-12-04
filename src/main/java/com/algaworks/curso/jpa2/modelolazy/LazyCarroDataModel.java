package com.algaworks.curso.jpa2.modelolazy;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import com.algaworks.curso.jpa2.dao.CarroDAO;
import com.algaworks.curso.jpa2.model.Carro;

public class LazyCarroDataModel extends LazyDataModel<Carro> implements Serializable{

	private CarroDAO carroDAO;

	public LazyCarroDataModel(CarroDAO carroDAO) {
		super();
		this.carroDAO = carroDAO;
	}
	
	
	/*@Override
	Esse nao e o metodo load certo.
	public List<Carro> load(int first, int pageSize, List<SortMeta> multiSortMeta, Map<String, Object> filters) {
		
	}*/
	
	/*Metodo load correto nao tem uma List, esse metodo ele retorna uma lista lazy de carros
	 * de acordo como o primeiro resultado e o tamanho*/
	@Override
	public List<Carro> load(int first, int pageSize, String sortField, SortOrder sortOrder,
			Map<String, Object> filters) {
		List<Carro> carros = this.carroDAO.buscarComPaginacao(first, pageSize);//Recebe o primeiro e o tamanho
		this.setRowCount(this.carroDAO.encontrarQuantidadeDeCarros().intValue());//Recebe a quantidades de linhas.
		return carros;//Retorna o resultado.
	}
	
	
}

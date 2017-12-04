package com.algaworks.curso.jpa2.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.inject.Inject;

import com.algaworks.curso.jpa2.dao.CarroDAO;
import com.algaworks.curso.jpa2.model.Carro;

@FacesConverter(forClass=Carro.class)
public class CarroConverter implements Converter {

	@Inject
	private CarroDAO carroDAO;
	
	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		Carro retorno = null;
		
		if (value != null) {
			retorno = this.carroDAO.buscarPeloCodigo(Long.parseLong(value));
		}
		
		return retorno;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		if (value != null) {
			Long codigo = ((Carro) value).getCodigo();
			return (codigo == null ? null : codigo.toString());
		
		}
		
		return "";
	}

}
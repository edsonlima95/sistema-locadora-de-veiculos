package com.algaworks.curso.jpa2.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.inject.Inject;

import com.algaworks.curso.jpa2.dao.MotoristaDAO;
import com.algaworks.curso.jpa2.model.Motorista;

@FacesConverter(forClass=Motorista.class)
public class MotoristaConverter implements Converter {

	@Inject
	private MotoristaDAO motoristaDAO;
	
	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		Motorista retorno = null;
		
		if (value != null) {
			retorno = this.motoristaDAO.buscarPeloCodigo(Long.parseLong(value));
		}

		return retorno;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		if (value != null) {
			Long codigo = ((Motorista) value).getCodigo();
			return  codigo == null ? null : codigo.toString();
		}
		
		return null;
	}

}
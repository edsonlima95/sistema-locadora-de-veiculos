package com.algaworks.curso.jpa2.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.inject.Inject;

import com.algaworks.curso.jpa2.dao.ModeloCarroDAO;
import com.algaworks.curso.jpa2.model.ModeloCarro;

@FacesConverter(forClass=ModeloCarro.class)
public class ModeloCarroConverter implements Converter {

	@Inject
	private ModeloCarroDAO modeloDAO;

	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {

		ModeloCarro retorno = null;
		if (value != null) {
			retorno = this.modeloDAO.buscarPeloCodigo(Long.parseLong(value));
		}

		return retorno;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {

		if (value != null) {
			Long codigo = ((ModeloCarro) value).getCodigo();
			return codigo == null ? null : codigo.toString();
		}

		return null;
	}

}

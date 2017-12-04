package com.algaworks.curso.jpa2.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.inject.Inject;

import com.algaworks.curso.jpa2.dao.FabricanteDAO;
import com.algaworks.curso.jpa2.model.Fabricante;
import com.algaworks.curso.jpa2.util.cdi.CDIServiceLocator;

@FacesConverter(forClass=Fabricante.class)
public class FabricanteConverter implements Converter {

	@Inject
	private FabricanteDAO fabricanteDAO;

	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		Fabricante retornaFabricante = null;
		
		if(value !=null ) {
			retornaFabricante = (Fabricante) this.fabricanteDAO.buscarPorCodigo(Long.parseLong(value));
		}		
				
		return retornaFabricante;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		if(value != null) {
			Long codigo = ((Fabricante) value).getCodigo();
			return codigo == null ? null : codigo.toString();
		}
		return null;
	}

}
